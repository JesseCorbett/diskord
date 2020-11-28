package com.jessecorbett.diskord.internal.client

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.exceptions.*
import com.jessecorbett.diskord.internal.epochMillisNow
import com.jessecorbett.diskord.internal.epochSecondNow
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.defaultJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import mu.KotlinLogging
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

private const val DISCORD_API_URL = "https://discord.com/api"

private val logger = KotlinLogging.logger {}

data class RateLimitInfo(val limit: Int, val remaining: Int, val reset: Long)

@OptIn(ExperimentalTime::class)
private suspend fun waitForRateLimit(rateLimitInfo: RateLimitInfo) {
    if (rateLimitInfo.remaining != 0) return
    val resetsAt = rateLimitInfo.reset - epochSecondNow()
    delay(resetsAt.seconds)
}

interface RestClient {
    suspend fun GET(
        majorPath: String,
        minorPath: String = "",
        rateKey: String = majorPath,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse

    suspend fun POST(
        majorPath: String,
        minorPath: String = "",
        rateKey: String = majorPath,
        omitNulls: Boolean = false,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse

    suspend fun PUT(
        majorPath: String,
        minorPath: String = "",
        rateKey: String = majorPath,
        omitNulls: Boolean = false,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse

    suspend fun PATCH(
        majorPath: String,
        minorPath: String = "",
        rateKey: String = majorPath,
        omitNulls: Boolean = false,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse

    suspend fun DELETE(
        majorPath: String,
        minorPath: String = "",
        rateKey: String = majorPath,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse
}

@DiskordInternals
class DefaultRestClient(userType: DiscordUserType, token: String, botUrl: String, botVersion: String) : RestClient {
    private val authorizationHeader = userType.type + " " + token
    private val userAgentHeader = "DiscordBot: ($botUrl, $botVersion)"

    private val defaultClient = buildClient()
    private val omitNullsClient = buildClient(true)

    private val pathToBucketMap: MutableMap<String, String> = mutableMapOf()
    private var globalRateLimit = RateLimitInfo(1, 1, Long.MAX_VALUE)
    private val rateLimitBuckets: MutableMap<String, RateLimitInfo> = mutableMapOf()

    fun close() {
        defaultClient.close()
        omitNullsClient.close()
    }

    private suspend fun request(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        method: HttpMethod,
        omitNulls: Boolean,
        block: HttpRequestBuilder.() -> Unit
    ): HttpResponse {
        // Wait on rate limits if any apply
        waitForRateLimit(globalRateLimit)
        pathToBucketMap[rateKey]?.let { bucket ->
            val rateLimit = rateLimitBuckets.getOrPut(bucket) { RateLimitInfo(1, 1, Long.MAX_VALUE) }
            waitForRateLimit(rateLimit)
        }

        val client = if (omitNulls) {
            omitNullsClient
        } else {
            defaultClient
        }

        // Construct and perform request
        val response: HttpResponse = client.request(DISCORD_API_URL + majorPath + minorPath) {
            this.method = method
            header("Authorization", authorizationHeader)
            header("User-Agent", userAgentHeader)
            contentType(ContentType.Application.Json)
            block()
        }

        // Update rate limit data store from response
        updateRateLimits(response.headers, rateKey)

        // Either return response, handle unexpected rate limit error, or throw other error
        return if (response.status.isSuccess()) {
            response
        } else {
            try {
                throwFailure(response.status.value, response.readText())
            } catch (rateLimitException: DiscordRateLimitException) {
                // We already address rate limit updates above, so just immediately queue a retry
                request(majorPath, minorPath, rateKey, method, omitNulls, block)
            }
        }
    }

    private fun updateRateLimits(headers: Headers, rateKey: String) {
        val isGlobal = headers.contains("X-RateLimit-Global")
        var bucket = headers["X-RateLimit-Bucket"]
        val limit = headers["X-RateLimit-Limit"]?.toInt() ?: 1
        val remaining = headers["X-RateLimit-Remaining"]?.toInt() ?: 1
        val resetAt = headers["X-RateLimit-Reset"]?.toLong() ?: Long.MAX_VALUE

        if (bucket == null) {
            logger.warn { "Encountered an API response without a rate limit bucket" }
            bucket = "fallback-bucket"
        } else {
            pathToBucketMap[rateKey] = bucket
        }

        if (isGlobal) {
            globalRateLimit = RateLimitInfo(1, 0, resetAt)
        } else {
            rateLimitBuckets.getOrPut(bucket) {
                RateLimitInfo(limit, remaining, resetAt)
            }
        }
    }

    override suspend fun GET(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        block: HttpRequestBuilder.() -> Unit
    ) = request(majorPath, minorPath, rateKey, HttpMethod.Get, false, block)

    override suspend fun POST(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        omitNulls: Boolean,
        block: HttpRequestBuilder.() -> Unit
    ) = request(majorPath, minorPath, rateKey, HttpMethod.Post, omitNulls, block)

    override suspend fun PUT(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        omitNulls: Boolean,
        block: HttpRequestBuilder.() -> Unit
    ) = request(majorPath, minorPath, rateKey, HttpMethod.Put, omitNulls, block)

    override suspend fun PATCH(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        omitNulls: Boolean,
        block: HttpRequestBuilder.() -> Unit
    ) = request(majorPath, minorPath, rateKey, HttpMethod.Patch, omitNulls, block)

    override suspend fun DELETE(
        majorPath: String,
        minorPath: String,
        rateKey: String,
        block: HttpRequestBuilder.() -> Unit
    ) = request(majorPath, minorPath, rateKey, HttpMethod.Delete, false, block)
}

@DiskordInternals
private fun throwFailure(code: Int, body: String?): Nothing = throw when (code) {
    400 -> DiscordBadRequestException(body)
    401 -> DiscordUnauthorizedException()
    403 -> DiscordBadPermissionsException()
    404 -> DiscordNotFoundException()
    429 -> defaultJson.decodeFromString(RateLimitExceeded.serializer(), body!!).let {
        logger.info { "Encountered a rate limit exception" }
        DiscordRateLimitException(it.message, (it.retryAfter + epochMillisNow()) / 1000, it.isGlobal)
    }
    502 -> DiscordGatewayException()
    in 500..599 -> DiscordInternalServerException()
    else -> DiscordException()
}
