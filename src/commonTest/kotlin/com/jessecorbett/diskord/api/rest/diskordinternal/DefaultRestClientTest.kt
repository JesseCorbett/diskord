package com.jessecorbett.diskord.api.rest.diskordinternal

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.jessecorbett.diskord.test.setupHttpClientMock
import com.jessecorbett.diskord.test.waitForTest
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.engine.mock.respond
import io.ktor.http.headersOf
import kotlin.test.Test

@DiskordInternals
internal class DefaultRestClientTest {
    @Test
    fun `should send get request`() {
        val requestUrl = "/endpoint/do-something"
        val responseText = "response"

        val httpClient = setupHttpClientMock {
            respond(responseText)
        }

        val client = DefaultRestClient(client = httpClient)
        assertThat(waitForTest { client.getRequest(requestUrl, mapOf()).body }).isEqualTo(responseText)
    }

    @Test
    fun `should send get request with headers`() {
        val requestUrl = "/endpoint/do-something"
        val requestHeaders = mapOf("Accept" to "application/json")

        val responseText = "response"
        val responseHeaders = headersOf("Content-Type" to listOf("application/json"))

        val httpClient = setupHttpClientMock(requestUrl) { request ->
            assertThat(request.headers["Accept"]).isEqualTo("application/json")

            respond(responseText, headers=responseHeaders)
        }

        val client = DefaultRestClient(client = httpClient)
        val response = waitForTest { client.getRequest(requestUrl, requestHeaders) }

        assertThat(response.body).isEqualTo(responseText)
        assertThat(response.headers).contains("Content-Type", "application/json")
    }
}
