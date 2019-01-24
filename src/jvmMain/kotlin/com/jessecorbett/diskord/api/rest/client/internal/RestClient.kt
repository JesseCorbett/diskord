package com.jessecorbett.diskord.api.rest.client.internal

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.exception.*
import com.jessecorbett.diskord.internal.defaultUserAgentUrl
import com.jessecorbett.diskord.internal.defaultUserAgentVersion
import com.jessecorbett.diskord.internal.httpClient
import kotlinx.coroutines.delay
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val discordApi = "https://discordapp.com/api"

private fun <T> jsonBody(serializer: KSerializer<T>, value: T) = RequestBody.create(MediaType.get("application/json; charset=utf-8"), Json.stringify(serializer, value))

private fun emptyBody() = RequestBody.create(MediaType.get("application/json; charset=utf-8"), "")

/**
 * A generic REST client for a discord resource with it's own rate limit scope.
 *
 * @property token The user's API token used for authentication.
 * @property userType The type of the user.
 * @property botUrl the URL of this bot.
 * @property botVersion what version of the bot this is.
 */
abstract class RestClient(
        private val token: String,
        private val userType: DiscordUserType,
        private val botUrl: String = defaultUserAgentUrl,
        private val botVersion: String = defaultUserAgentVersion
) {
    /**
     * The rate limit info for this discord object.
     */
    val rateLimitInfo = RateLimitInfo(1, 1, Instant.MAX)

    private fun captureFailure(response: Response) = when (response.code()) {
            400 -> DiscordBadRequestException(response.body()?.string())
            401 -> DiscordUnauthorizedException()
            403 -> DiscordBadPermissionsException()
            404 -> DiscordNotFoundException()
            429 -> response.body()?.string()?.let { Json.nonstrict.parse(RateLimitExceeded.serializer(), it) }!!.let {
                DiscordRateLimitException(it.message, Instant.now().plusMillis(it.retryAfter).epochSecond, it.isGlobal)
            }
            502 -> DiscordGatewayException()
            in 500..599 -> DiscordInternalServerException()
            else -> DiscordException()
    }

    private fun commonRequest(url: String) = Request.Builder()
            .url(discordApi + url)
            .header("Authorization", "$userType $token")
            .header("User-Agent", "DiscordBot: ($botUrl, $botVersion)")

    private suspend fun makeRequest(request: Request, rateLimit: RateLimitInfo): Response {
        if (rateLimit.remaining < 1) {
            delay(rateLimit.resetTime.toEpochMilli() - Instant.now().toEpochMilli())
        }

        return try {
            suspendCoroutine { cont ->
                httpClient.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, exception: IOException) {
                        cont.resumeWithException(exception)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val instantAtDiscordServer = DateTimeFormatter.RFC_1123_DATE_TIME.parse(response.header("Date"), Instant::from)
                        val discordAheadBySeconds = instantAtDiscordServer.epochSecond - Instant.now().epochSecond // Count the seconds in the future Discord is

                        rateLimitInfo.limit = response.header("X-RateLimit-Limit")?.toInt() ?: rateLimit.limit
                        rateLimit.remaining = response.header("X-RateLimit-Remaining")?.toInt() ?: rateLimit.remaining
                        rateLimit.resetTime = Instant.ofEpochSecond(response.headers().get("X-RateLimit-Reset")?.toLong()?.plus(discordAheadBySeconds) ?: rateLimit.resetTime.epochSecond)

                        if (!response.isSuccessful) {
                            cont.resumeWithException(captureFailure(response))
                            return
                        }

                        cont.resume(response)
                    }
                })
            }
        } catch (rateLimitException: DiscordRateLimitException) {
            makeRequest(request, rateLimit)
        }
    }

    /**
     * Make a GET request for this discord object.
     *
     * @param url The url of the request.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @throws DiscordException representing an API error.
     */
    protected suspend fun getRequest(url: String, rateLimit: RateLimitInfo = rateLimitInfo)
            = makeRequest(commonRequest(url).get().build(), rateLimit)

    /**
     * Make a POST request for this discord object.
     *
     * @param url The url of the request.
     * @param body The data to send with the API request.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @return the API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun <T> postRequest(url: String, body: T, serializer: KSerializer<T>, rateLimit: RateLimitInfo = rateLimitInfo)
            = makeRequest(commonRequest(url).post(jsonBody(serializer, body)).build(), rateLimit)

    /**
     * Make a POST request for this discord object.
     *
     * @param url The url of the request.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @return the API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun postRequest(url: String, rateLimit: RateLimitInfo = rateLimitInfo)
            = makeRequest(commonRequest(url).post(emptyBody()).build(), rateLimit)

    /**
     * Make a PUT request for this discord object.
     *
     * @param url The url of the request.
     * @param body The data to send with the API request.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @return the API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun <T> putRequest(url: String, body: T, serializer: KSerializer<T>, rateLimit: RateLimitInfo = rateLimitInfo) = makeRequest(commonRequest(url).put(jsonBody(serializer, body)).build(), rateLimit)

    /**
     * Make a PUT request for this discord object.
     *
     * @param url The url of the request.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @return the API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun putRequest(url: String, rateLimit: RateLimitInfo = rateLimitInfo) = makeRequest(commonRequest(url).put(emptyBody()).build(), rateLimit)

    /**
     * Make a PATCH request for this discord object.
     *
     * @param url The url of the request.
     * @param body The data to send with the API request.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @return the API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun <T> patchRequest(url: String, body: T, serializer: KSerializer<T>, rateLimit: RateLimitInfo = rateLimitInfo) = makeRequest(commonRequest(url).patch(jsonBody(serializer, body)).build(), rateLimit)

    /**
     * Make a DELETE request for this discord object.
     *
     * @param url The url of the request.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @return the API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun deleteRequest(url: String, rateLimit: RateLimitInfo = rateLimitInfo) = makeRequest(commonRequest(url).delete().build(), rateLimit)
}
