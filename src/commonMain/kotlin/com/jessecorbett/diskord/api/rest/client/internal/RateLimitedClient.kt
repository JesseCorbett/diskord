package com.jessecorbett.diskord.api.rest.client.internal

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.exception.*
import com.jessecorbett.diskord.internal.*
import kotlinx.coroutines.delay
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

private fun captureFailure(code: Int, body: String?) = when (code) {
    400 -> DiscordBadRequestException(body)
    401 -> DiscordUnauthorizedException()
    403 -> DiscordBadPermissionsException()
    404 -> DiscordNotFoundException()
    429 -> Json.nonstrict.parse(RateLimitExceeded.serializer(), body!!).let {
        DiscordRateLimitException(it.message, (it.retryAfter + epochMillisNow()) / 1000, it.isGlobal)
    }
    502 -> DiscordGatewayException()
    in 500..599 -> DiscordInternalServerException()
    else -> DiscordException()
}

private suspend fun doRequest(rateLimit: RateLimitInfo, request: suspend () -> Response): Response {
    if (rateLimit.remaining < 1) {
        delay(rateLimit.resetTime - epochSecondNow())
    }

    var response = request()

    val instantAtDiscordServer = parseRfc1123(response.headers["Date"] ?: response.headers.getValue("date")!!)
    val discordAheadBySeconds = instantAtDiscordServer - epochSecondNow() // Count the seconds in the future Discord is

    rateLimit.limit = response.headers["X-RateLimit-Limit"]?.toInt() ?: rateLimit.limit
    rateLimit.remaining = response.headers["X-RateLimit-Remaining"]?.toInt() ?: rateLimit.remaining
    rateLimit.resetTime =
        response.headers["X-RateLimit-Reset"]?.toLong()?.plus(discordAheadBySeconds) ?: rateLimit.resetTime

    if (response.code !in 200..299) {
        try {
            throw captureFailure(response.code, response.body)
        } catch (exception: DiscordRateLimitException) {
            response = doRequest(rateLimit, request)
        }
    }

    return response
}


abstract class RateLimitedClient(
    token: String,
    userType: DiscordUserType,
    botUrl: String = defaultUserAgentUrl,
    botVersion: String = defaultUserAgentVersion
) : BaseRestClient() {
    /**
     * The rate limit info for this discord object.
     */
    val rateLimitInfo = RateLimitInfo(1, 1, Long.MAX_VALUE)

    private val commonHeaders = mapOf(
        "Authorization" to userType.type + " " + token,
        "User-Agent" to "DiscordBot: ($botUrl, $botVersion)"
    )

    /**
     * Make a GET request for this discord object.
     *
     * @param url The url of the request.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @throws DiscordException representing an API error.
     */
    protected suspend fun <R> getRequest(
        url: String,
        deserializer: DeserializationStrategy<R>,
        rateLimit: RateLimitInfo = rateLimitInfo
    ): R {
        val response = doRequest(rateLimit) {
            super.getRequest(url, commonHeaders)
        }

        return Json.nonstrict.parse(deserializer, response.body!!)
    }

    /**
     * Make a POST request for this discord object.
     *
     * @param url The url of the request.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @return the API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun postRequest(url: String, rateLimit: RateLimitInfo = rateLimitInfo) {
        doRequest(rateLimit) {
            super.postRequest(url, null, commonHeaders)
        }
    }

    /**
     * Make a POST request for this discord object.
     *
     * @param url The url of the request.
     * @param body The data to send with the API request.
     * @param serializer The request serializer.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @return the API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun <T> postRequest(
        url: String,
        body: T,
        serializer: SerializationStrategy<T>,
        rateLimit: RateLimitInfo = rateLimitInfo
    ) {
        doRequest(rateLimit) {
            super.postRequest(url, Json.nonstrict.stringify(serializer, body), commonHeaders)
        }
    }

    /**
     * Make a POST request for this discord object.
     *
     * @param url The url of the request.
     * @param deserializer The response deserializer.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @return the API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun <R> postRequest(
        url: String,
        deserializer: DeserializationStrategy<R>,
        rateLimit: RateLimitInfo = rateLimitInfo
    ): R {
        val response = doRequest(rateLimit) {
            super.postRequest(url, null, commonHeaders)
        }

        return Json.nonstrict.parse(deserializer, response.body!!)
    }

    /**
     * Make a POST request for this discord object.
     *
     * @param url The url of the request.
     * @param deserializer The response deserializer.
     * @param rateLimit the rate limit info used for waiting if rate limited.
     *
     * @return the API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun <T, R> postRequest(
        url: String,
        body: T,
        serializer: SerializationStrategy<T>,
        deserializer: DeserializationStrategy<R>,
        rateLimit: RateLimitInfo = rateLimitInfo
    ): R {
        val response = doRequest(rateLimit) {
            super.postRequest(url, Json.nonstrict.stringify(serializer, body), commonHeaders)
        }

        return Json.nonstrict.parse(deserializer, response.body!!)
    }

    /**
     * Make a PUT request for this discord object.
     *
     * @param url The url of the request.
     * @param rateLimit The rate limit info used for waiting if rate limited.
     *
     * @return The API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun putRequest(url: String, rateLimit: RateLimitInfo = rateLimitInfo) {
        doRequest(rateLimit) {
            super.putRequest(url, "", commonHeaders)
        }
    }

    /**
     * Make a PUT request for this discord object.
     *
     * @param url The url of the request.
     * @param body The data to send with the API request.
     * @param serializer The request serializer.
     * @param rateLimit The rate limit info used for waiting if rate limited.
     *
     * @return The API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun <T> putRequest(
        url: String,
        body: T,
        serializer: SerializationStrategy<T>,
        rateLimit: RateLimitInfo = rateLimitInfo
    ) {
        doRequest(rateLimit) {
            super.putRequest(url, Json.nonstrict.stringify(serializer, body), commonHeaders)
        }
    }

    /**
     * Make a PUT request for this discord object.
     *
     * @param url The url of the request.
     * @param deserializer The response deserializer.
     * @param rateLimit The rate limit info used for waiting if rate limited.
     *
     * @return The API response.
     * @throws DiscordException Representing an API error.
     */
    protected suspend fun <R> putRequest(
        url: String,
        deserializer: DeserializationStrategy<R>,
        rateLimit: RateLimitInfo = rateLimitInfo
    ): R {
        val response = doRequest(rateLimit) {
            super.putRequest(url, null, commonHeaders)
        }

        return Json.nonstrict.parse(deserializer, response.body!!)
    }

    /**
     * Make a PUT request for this discord object.
     *
     * @param url The url of the request.
     * @param deserializer The response deserializer.
     * @param rateLimit The rate limit info used for waiting if rate limited.
     *
     * @return The API response.
     * @throws DiscordException Representing an API error.
     */
    protected suspend fun <T, R> putRequest(
        url: String,
        body: T,
        serializer: SerializationStrategy<T>,
        deserializer: DeserializationStrategy<R>,
        rateLimit: RateLimitInfo = rateLimitInfo
    ): R {
        val response = doRequest(rateLimit) {
            super.putRequest(url, Json.nonstrict.stringify(serializer, body), commonHeaders)
        }

        return Json.nonstrict.parse(deserializer, response.body!!)
    }

    /**
     * Make a PATCH request for this discord object.
     *
     * @param url The url of the request.
     * @param body The data to send with the API request.
     * @param serializer The request serializer.
     * @param rateLimit The rate limit info used for waiting if rate limited.
     *
     * @throws DiscordException representing an API error.
     */
    protected suspend fun <T> patchRequest(
        url: String,
        body: T,
        serializer: SerializationStrategy<T>,
        rateLimit: RateLimitInfo = rateLimitInfo
    ) {
        doRequest(rateLimit) {
            super.patchRequest(url, Json.nonstrict.stringify(serializer, body), commonHeaders)
        }
    }

    /**
     * Make a PATCH request for this discord object.
     *
     * @param url The url of the request.
     * @param body The data to send with the API request.
     * @param serializer The request serializer.
     * @param deserializer The response deserializer.
     * @param rateLimit The rate limit info used for waiting if rate limited.
     *
     * @return The API response.
     * @throws DiscordException representing an API error.
     */
    protected suspend fun <T, R> patchRequest(
        url: String,
        body: T,
        serializer: SerializationStrategy<T>,
        deserializer: DeserializationStrategy<R>,
        rateLimit: RateLimitInfo = rateLimitInfo
    ): R {
        val response = doRequest(rateLimit) {
            super.patchRequest(url, Json.nonstrict.stringify(serializer, body), commonHeaders)
        }

        return Json.parse(deserializer, response.body!!)
    }

    /**
     * Make a DELETE request for this discord object.
     *
     * @param url The url of the request.
     * @param rateLimit The rate limit info used for waiting if rate limited.
     *
     * @throws DiscordException Representing an API error.
     */
    protected suspend fun deleteRequest(url: String, rateLimit: RateLimitInfo = rateLimitInfo) {
        doRequest(rateLimit) {
            super.deleteRequest(url, commonHeaders)
        }
    }

    /**
     * Make a DELETE request for this discord object.
     *
     * @param url The url of the request.
     * @param deserializer The response deserializer.
     * @param rateLimit The rate limit info used for waiting if rate limited.
     *
     * @return The API response.
     * @throws DiscordException Representing an API error.
     */
    protected suspend fun <R> deleteRequest(
        url: String,
        deserializer: DeserializationStrategy<R>,
        rateLimit: RateLimitInfo = rateLimitInfo
    ): R {
        val response = doRequest(rateLimit) {
            super.deleteRequest(url, commonHeaders)
        }

        return Json.nonstrict.parse(deserializer, response.body!!)
    }
}
