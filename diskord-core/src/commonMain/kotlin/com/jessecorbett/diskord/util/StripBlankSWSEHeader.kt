package com.jessecorbett.diskord.util

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

/**
 * Workaround interceptor to ensure that the `Sec-WebSocket-Extensions` header is stripped if empty.
 *
 * This feature should be installed after the `WebSockets` feature has been installed.
 *
 * See: https://youtrack.jetbrains.com/issue/KTOR-2388 (this can be removed once a fix has been picked up)
 */
internal class StripBlankSWSEHeader {
    private fun stripHeader(context: HttpRequestBuilder) {
        if (HttpHeaders.SecWebSocketExtensions in context.headers
                && context.headers[HttpHeaders.SecWebSocketExtensions]?.isBlank() == true) {
            context.headers.remove(HttpHeaders.SecWebSocketExtensions)
        }
    }

    class EmptyConfig

    internal companion object Feature : HttpClientFeature<EmptyConfig, StripBlankSWSEHeader> {
        override val key: AttributeKey<StripBlankSWSEHeader> = AttributeKey("StripEmptyWSExtensionHeader")

        override fun prepare(block: EmptyConfig.() -> Unit): StripBlankSWSEHeader {
            return StripBlankSWSEHeader()
        }

        override fun install(feature: StripBlankSWSEHeader, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Render) {
                feature.stripHeader(context)

                proceed()
            }
        }
    }
}
