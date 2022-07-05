package com.jessecorbett.diskord.internal.client

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.exceptions.*
import com.jessecorbett.diskord.internal.defaultUserAgentUrl
import com.jessecorbett.diskord.internal.defaultUserAgentVersion
import com.jessecorbett.diskord.internal.epochMillisNow
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.auditLogEntryJson
import com.jessecorbett.diskord.util.defaultJson
import com.jessecorbett.diskord.util.omitNullsJson
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import mu.KotlinLogging
import kotlin.math.ceil
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

private const val DISCORD_API_URL = "https://discord.com/api"

private val logger = KotlinLogging.logger {}

private data class RateLimitInfo(val limit: Int, val remaining: Int, val reset: Float)

@OptIn(ExperimentalTime::class)
private suspend fun waitForRateLimit(rateLimitInfo: RateLimitInfo) {
    // TODO: Handle time drift
    if (rateLimitInfo.remaining != 0) return
    val resetsAt = ceil(rateLimitInfo.reset * 1000).toLong() - epochMillisNow()

    when {
        resetsAt < 5000 -> logger.debug { "Delaying API call to satisfy low rate limit reset of ${resetsAt}ms" }
        resetsAt in 5000..10000 -> logger.info { "Delaying API call to satisfy rate limit reset of ${resetsAt}ms" }
        resetsAt > 10000 -> logger.info { "Delaying API call to satisfy high rate limit reset of ${resetsAt}ms. If you frequently encounter this consider reducing API calls" }
    }

    delay(resetsAt.milliseconds)
}

public interface RestClient {
    @DiskordInternals
    public suspend fun GET(
        majorPath: String,
        minorPath: String = "",
        rateKey: String = majorPath,
        auditLogs: Boolean = false,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse

    @DiskordInternals
    public suspend fun POST(
        majorPath: String,
        minorPath: String = "",
        rateKey: String = majorPath,
        omitNulls: Boolean = false,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse

    @DiskordInternals
    public suspend fun PUT(
        majorPath: String,
        minorPath: String = "",
        rateKey: String = majorPath,
        omitNulls: Boolean = false,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse

    @DiskordInternals
    public suspend fun PATCH(
        majorPath: String,
        minorPath: String = "",
        rateKey: String = majorPath,
        omitNulls: Boolean = false,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse

    @DiskordInternals
    public suspend fun DELETE(
        majorPath: String,
        minorPath: String = "",
        rateKey: String = majorPath,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse

    public companion object {

        /**
         * Returns an instance of [RestClient] with default configuration values
         *
         * @param token The API token
         *
         * @return A new instance of [RestClient]
         */
        @OptIn(DiskordInternals::class)
        public fun default(token: String): RestClient {
            return DefaultRestClient(DiscordUserType.BOT, token)
        }
    }
}

@DiskordInternals
public class DefaultRestClient(
    userType: DiscordUserType,
    token: String,
    botUrl: String = defaultUserAgentUrl,
    botVersion: String = defaultUserAgentVersion
) : RestClient {
    private val authorizationHeader = userType.type + " " + token
    private val userAgentHeader = "DiscordBot: ($botUrl, $botVersion)"

    private val defaultClient = buildClient(defaultJson)
    private val omitNullsClient = buildClient(omitNullsJson)
    // Not often used, so we'll lazily initialize it just to be safe
    private val auditLogsClient by lazy { buildClient(auditLogEntryJson) }

    private val pathToBucketMap: MutableMap<String, String> = mutableMapOf()
    private var globalRateLimit = RateLimitInfo(1, 1, Float.MAX_VALUE)
    private val rateLimitBuckets: MutableMap<String, RateLimitInfo> = mutableMapOf()

    /**
     * Closes the backing client instances
     */
    public fun close() {
        defaultClient.close()
        omitNullsClient.close()
        auditLogsClient.close()
    }

    @OptIn(ExperimentalTime::class)
    private suspend fun request(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        method: HttpMethod,
        omitNulls: Boolean,
        auditLogs: Boolean,
        block: HttpRequestBuilder.() -> Unit
    ): HttpResponse {
        // Wait on rate limits if any apply
        waitForRateLimit(globalRateLimit)
        pathToBucketMap[rateKey]?.let { bucket ->
            val rateLimit = rateLimitBuckets.getOrPut(bucket) { RateLimitInfo(1, 1, Float.MAX_VALUE) }
            waitForRateLimit(rateLimit)
        }

        val client = when {
            omitNulls -> omitNullsClient
            auditLogs -> auditLogsClient
            else -> defaultClient
        }

        // Construct and perform request
        val response: HttpResponse = client.request(DISCORD_API_URL + majorPath + minorPath) {
            this.method = method
            header("Authorization", authorizationHeader)
            header("User-Agent", userAgentHeader)
            block()
            if (body !is MultiPartFormDataContent) {
                contentType(ContentType.Application.Json)
            }

            logger.debug { "Outgoing contentType is: ${contentType()}" }
        }

        // Update rate limit data store from response
        updateRateLimits(response.headers, rateKey)

        // Either return response, handle unexpected rate limit error, or throw other error
        return if (response.status.isSuccess()) {
            response
        } else {
            try {
                throwFailure(response.status.value, response.bodyAsText(), response)
            } catch (rateLimitException: DiscordRateLimitException) {
                logger.info { "Attempting to recover from rate limit error with a retry after ${rateLimitException.retryAfterSeconds}s" }
                // We already address rate limit updates above, so just immediately queue a retry after waiting
                delay(rateLimitException.retryAfterSeconds.seconds)
                logger.info { "Attempting retry" }
                request(majorPath, minorPath, rateKey, method, omitNulls, auditLogs, block)
            }
        }
    }

    private fun updateRateLimits(headers: Headers, rateKey: String) {
        val isGlobal = headers.contains("X-RateLimit-Global")
        var bucket = headers["X-RateLimit-Bucket"]
        val limit = headers["X-RateLimit-Limit"]?.toInt() ?: 1
        val remaining = headers["X-RateLimit-Remaining"]?.toInt() ?: 1
        val resetAt = headers["X-RateLimit-Reset"]?.toFloat() ?: Float.MAX_VALUE

        // Sets the associated bucket in case we don't know it yet
        if (bucket == null) {
            logger.warn { "Encountered an API response without a rate limit bucket, using a fallback bucket for safety" }
            bucket = "fallback-bucket"
        } else {
            pathToBucketMap[rateKey] = bucket
        }

        if (isGlobal) {
            globalRateLimit = RateLimitInfo(1, 0, resetAt)
        } else {
            rateLimitBuckets[bucket] = RateLimitInfo(limit, remaining, resetAt)
        }
    }

    override suspend fun GET(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        auditLogs: Boolean,
        block: HttpRequestBuilder.() -> Unit
    ): HttpResponse = request(majorPath, minorPath, rateKey, HttpMethod.Get, false, auditLogs, block)

    override suspend fun POST(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        omitNulls: Boolean,
        block: HttpRequestBuilder.() -> Unit
    ): HttpResponse = request(majorPath, minorPath, rateKey, HttpMethod.Post, omitNulls, false, block)

    override suspend fun PUT(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        omitNulls: Boolean,
        block: HttpRequestBuilder.() -> Unit
    ): HttpResponse = request(majorPath, minorPath, rateKey, HttpMethod.Put, omitNulls, false, block)

    override suspend fun PATCH(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        omitNulls: Boolean,
        block: HttpRequestBuilder.() -> Unit
    ): HttpResponse = request(majorPath, minorPath, rateKey, HttpMethod.Patch, omitNulls, false, block)

    override suspend fun DELETE(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        block: HttpRequestBuilder.() -> Unit
    ): HttpResponse = request(majorPath, minorPath, rateKey, HttpMethod.Delete, false, false, block)
}

@DiskordInternals
private fun throwFailure(code: Int, body: String?, httpResponse: HttpResponse): Nothing {
    val exception = when (code) {
        400 -> DiscordBadRequestException(body)
        401 -> DiscordUnauthorizedException()
        403 -> DiscordBadPermissionsException()
        404 -> DiscordNotFoundException()
        429 -> defaultJson.decodeFromString(RateLimitExceeded.serializer(), body!!).let {
            logger.info { "Encountered a rate limit exception" }
            // Converting from millis to seconds as it appears the API is doing so despite documentation
            DiscordRateLimitException(it.message, it.retryAfterSeconds / 1000, it.isGlobal)
        }
        502 -> DiscordGatewayException()
        in 500..599 -> DiscordInternalServerException()
        else -> DiscordCompatibilityException("An unhandled HTTP status code $code was thrown")
    }

    val exceptionMessage = listOfNotNull(exception::class.simpleName, exception.message).joinToString(" ")
    val method = httpResponse.request.method.value
    val path = httpResponse.request.url.encodedPath
    logger.warn { "Encountered $exceptionMessage making API call $method $path" }

    throw exception
}
