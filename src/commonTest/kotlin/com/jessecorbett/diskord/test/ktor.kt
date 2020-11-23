package com.jessecorbett.diskord.test

import com.jessecorbett.diskord.internal.client.DISCORD_API_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.fullPath
import io.ktor.http.hostWithPort

/**
 * Create an HTTP client mock which calls the provided block when the specified URL is hit.
 *
 * @param url the url to handle
 * @param baseUrl the url prefix (defaults to [DISCORD_API_URL])
 * @param relaxed throw an exception if `true`, otherwise return [defaultErrorCode]
 * @param defaultErrorCode the error code to return when [relaxed] is `true`
 * @param block the block to call when a request is processed for the specified URL
 */
internal fun setupHttpClientMock(
    url: String,
    baseUrl: String = DISCORD_API_URL,
    relaxed: Boolean = false,
    defaultErrorCode: HttpStatusCode = HttpStatusCode.NotFound,
    block: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData
) = setupHttpClientMock(url, baseUrl, {
    if (relaxed) {
        respondError(defaultErrorCode)
    } else {
        error("Received un-processable URL")
    }
}, block)

/**
 * Create an HTTP client mock which calls the provided block when the specified URL is hit.
 *
 * @param url the url to handle
 * @param baseUrl the url prefix (defaults to [DISCORD_API_URL])
 * @param errorBlock the block to call when a request is processed for any URL other than the specified URL
 * @param block the block to call when a request is processed for the specified URL
 */
internal fun setupHttpClientMock(
    url: String,
    baseUrl: String = DISCORD_API_URL,
    errorBlock: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData,
    block: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData
) = setupHttpClientMock { request ->
    when (request.url.fullUrl) {
        "$baseUrl$url" -> block(request)
        else -> errorBlock(request)
    }
}

/**
 * Create an HTTP client which registers the specified block as a mock request handler.
 *
 * @param block the block to call when a request is processed
 */
internal fun setupHttpClientMock(
    block: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData
) = HttpClient(MockEngine) {
    engine {
        addHandler { request ->
            block(request)
        }
    }
}

/**
 * Get the URL with the specified host and port (if it is not the default port).
 */
internal val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort

/**
 * Get the URL with the scheme, hostname, and full path.
 */
internal val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"
