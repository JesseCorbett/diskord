package com.jessecorbett.diskord.api.client.internal

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.exception.*
import com.jessecorbett.diskord.internal.*
import kotlinx.coroutines.delay
import okhttp3.*
import java.io.IOException
import java.time.Instant
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val discordApi = "https://discordapp.com/api"

private fun jsonBody(value: Any?) = RequestBody.create(MediaType.get("application/json; charset=utf-8"), jsonMapper.writeValueAsString(value))

abstract class RestClient(val token: String, val userType: DiscordUserType) {
    private val rateInfo = RateLimitInfo(1, 1, Instant.MAX)
    var botUrl: String = defaultUserAgentUrl
    var botVersion: String = defaultUserAgentVersion

    fun getRateLimit() = rateInfo.limit

    fun getRateRemaining() = rateInfo.remaining

    fun getRateResetTime() = rateInfo.resetTime

    private fun captureFailure(response: Response) {
        when (response.code()) {
            400 -> throw DiscordBadRequestException(response.body()?.string())
            401 -> throw DiscordUnauthorizedException()
            403 -> throw DiscordBadPermissionsException()
            404 -> throw DiscordNotFoundException()
            429 -> response.bodyAs<RateLimitExceeded>().let {
                throw DiscordRateLimitException(it.message, Instant.now().plusMillis(it.retryAfter), it.isGlobal)
            }
            502 -> throw DiscordGatewayException()
            in 500..599 -> throw DiscordInternalServerException()
        }
    }

    private fun commonRequest(url: String) = Request.Builder()
            .url(discordApi + url)
            .header("Authorization", "$userType $token")
            .header("User-Agent", "DiscordBot: ($botUrl, $botVersion)")

    private suspend fun makeRequest(request: Request, rateLimit: RateLimitInfo): Response {
        if (rateLimit.remaining < 1) {
            delay(rateLimit.resetTime.toEpochMilli() - Instant.now().toEpochMilli())
        }

        try {
            return suspendCoroutine { cont ->
                httpClient.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, exception: IOException) {
                        cont.resumeWithException(exception)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        rateInfo.limit = response.header("X-RateLimit-Limit")?.toInt() ?: rateLimit.limit
                        rateLimit.remaining = response.header("X-RateLimit-Remaining")?.toInt() ?: rateLimit.remaining
                        rateLimit.resetTime = Instant.ofEpochSecond(response.headers().get("X-RateLimit-Reset")?.toLong()
                                ?: rateLimit.resetTime.epochSecond)

                        if (!response.isSuccessful) {
                            try {
                                captureFailure(response)
                            } catch (discordException: DiscordException) {
                                cont.resumeWithException(discordException)
                                return
                            }
                        }

                        cont.resume(response)
                    }
                })
            }
        } catch (rateLimitException: DiscordRateLimitException) {
            return makeRequest(request, rateLimit)
        }
    }

    protected suspend fun getRequest(url: String, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).get().build(), rateLimit)

    protected suspend fun postRequest(url: String, body: Any, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).post(jsonBody(body)).build(), rateLimit)

    protected suspend fun postRequest(url: String, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).post(jsonBody(null)).build(), rateLimit)

    protected suspend fun putRequest(url: String, body: Any, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).put(jsonBody(body)).build(), rateLimit)

    protected suspend fun putRequest(url: String, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).put(jsonBody(null)).build(), rateLimit)

    protected suspend fun patchRequest(url: String, body: Any, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).patch(jsonBody(body)).build(), rateLimit)

    protected suspend fun deleteRequest(url: String, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).delete().build(), rateLimit)
}
