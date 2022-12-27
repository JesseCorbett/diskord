package com.jessecorbett.diskord.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

private const val TEST_ENDPOINT = "https://test.com"

internal class StripBlankSWSEHeaderTest {
    private fun setupClient(): HttpClient {
        return HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    val extensions = request.headers[HttpHeaders.SecWebSocketExtensions]
                    if (extensions != null && extensions.isBlank()) {
                        respondError(HttpStatusCode.BadRequest)
                    } else {
                        respond("OK")
                    }
                }
            }

            install(StripBlankSWSEHeader)
        }
    }

    @Test
    fun `should strip correct header if blank`(): Unit = runBlocking {
        val client = setupClient()
        val response = client.get(TEST_ENDPOINT) {
            header(HttpHeaders.SecWebSocketExtensions, "")
        }

        assertThat(response.status).isEqualTo(HttpStatusCode.OK)
    }

    @Test
    fun `should not strip correct header if not blank`(): Unit = runBlocking {
        val client = setupClient()
        val response = client.get(TEST_ENDPOINT) {
            header(HttpHeaders.SecWebSocketExtensions, "not empty")
        }

        assertThat(response.status).isEqualTo(HttpStatusCode.OK)
    }
}
