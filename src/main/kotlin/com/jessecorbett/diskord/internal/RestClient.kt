package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.api.rest.response.RateLimitExceeded
import com.jessecorbett.diskord.exception.*
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.time.Instant

private const val discordApi = "https://discordapp.com/api"

private fun jsonBody(value: Any?): RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonMapper.writeValueAsString(value))

abstract class RestClient(val token: String) {
    private val rateInfo = RateLimitInfo(1, 1, Instant.MAX)

    private fun handleFailure(response: Response) {
        when (response.code()) {
            400 -> throw DiscordBadRequestException()
            401 -> throw DiscordUnauthorizedException()
            403 -> throw DiscordBadPermissionsException()
            404 -> throw DiscordNotFoundException()
            429 -> {
                val rateLimitInfo = response.bodyAs(RateLimitExceeded::class)
                throw DiscordRateLimitException(rateLimitInfo.message, Instant.now().plusMillis(rateLimitInfo.retryAfter), rateLimitInfo.isGlobal)
            }
            502 -> throw DiscordGatewayException()
        }
        if (response.code() in 500..599) {
            throw DiscordInternalServerException()
        }
    }

    private fun commonRequest(url: String): Request.Builder = Request.Builder().url(discordApi + url).header("Authorization", "Bot $token")

    private fun makeRequest(request: Request, rateLimit: RateLimitInfo): Response {
        val response = httpClient.newCall(request).execute()

        rateLimit.limit = response.header("X-RateLimit-Limit")?.toInt() ?: rateLimit.limit
        rateLimit.remaining = response.header("X-RateLimit-Remaining")?.toInt() ?: rateLimit.remaining
        rateLimit.resetTime = Instant.ofEpochSecond(response.headers().get("X-RateLimit-Reset")?.toLong() ?: rateLimit.resetTime.epochSecond)

        if (!response.isSuccessful) {
            handleFailure(response)
        }

        return response
    }

    protected fun getRequest(url: String, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).get().build(), rateLimit)

    protected fun postRequest(url: String, body: Any, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).post(jsonBody(body)).build(), rateLimit)

    protected fun postRequest(url: String, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).post(jsonBody(null)).build(), rateLimit)

    protected fun putRequest(url: String, body: Any, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).put(jsonBody(body)).build(), rateLimit)

    protected fun putRequest(url: String, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).put(jsonBody(null)).build(), rateLimit)

    protected fun patchRequest(url: String, body: Any, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).patch(jsonBody(body)).build(), rateLimit)

    protected fun deleteRequest(url: String, rateLimit: RateLimitInfo = rateInfo) = makeRequest(commonRequest(url).delete().build(), rateLimit)
}
