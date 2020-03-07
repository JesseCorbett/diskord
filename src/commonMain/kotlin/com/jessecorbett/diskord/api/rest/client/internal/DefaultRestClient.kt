package com.jessecorbett.diskord.api.rest.client.internal

import com.jessecorbett.diskord.internal.httpClient
import com.jessecorbett.diskord.util.DEBUG_MODE
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.HttpClient
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.http.ContentType
import io.ktor.http.content.PartData
import io.ktor.http.content.TextContent
import io.ktor.utils.io.readUTF8Line

private const val BOT_AUTH_PREFIX = "-> Authorization: Bot"

private val DEFAULT_CLIENT = HttpClient(httpClient()) {
    install(Logging) {
        logger = object : Logger {
            private val delegate = Logger.DEFAULT
            override fun log(message: String) {
                delegate.log(if (!DEBUG_MODE && message.startsWith(BOT_AUTH_PREFIX)) {
                    "$BOT_AUTH_PREFIX <token hidden>"
                } else {
                    message
                })
            }
        }
        level = if (DEBUG_MODE) {
            LogLevel.ALL
        } else {
            LogLevel.HEADERS
        }
    }
}

@DiskordInternals
class DefaultRestClient(
    private val baseUrl: String = DISCORD_API_URL,
    private val client: HttpClient = DEFAULT_CLIENT
) : RestClient {
    private val contentType = ContentType.parse("application/json")

    override suspend fun getRequest(url: String, headers: Map<String, String>): Response {
        val result = client.get<HttpStatement>(baseUrl + url) {
            headers.forEach { header(it.key, it.value) }
        }.execute()
        return result.toResponse()
    }

    override suspend fun postRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response {
        val result = client.post<HttpStatement>(baseUrl + url) {
            headers.forEach { header(it.key, it.value) }
            if (jsonBody != null) {
                body = TextContent(jsonBody, contentType)
            }
        }.execute()
        return result.toResponse()
    }

    override suspend fun postMultipartRequest(
        url: String,
        parts: List<PartData>,
        headers: Map<String, String>
    ): Response {
        val result = client.submitFormWithBinaryData<HttpStatement>(baseUrl + url, parts) {
            headers.forEach { header(it.key, it.value) }
        }.execute()
        return result.toResponse()
    }

    override suspend fun putRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response {
        val result = client.put<HttpStatement>(baseUrl + url) {
            headers.forEach { header(it.key, it.value) }
            if (jsonBody != null) {
                body = TextContent(jsonBody, contentType)
            }
        }.execute()
        return result.toResponse()
    }

    override suspend fun patchRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response {
        val result = client.patch<HttpStatement>(baseUrl + url) {
            headers.forEach { header(it.key, it.value) }
            if (jsonBody != null) {
                body = TextContent(jsonBody, contentType)
            }
        }.execute()
        return result.toResponse()
    }

    override suspend fun deleteRequest(url: String, headers: Map<String, String>): Response {
        val result = client.delete<HttpStatement>(baseUrl + url) {
            headers.forEach { header(it.key, it.value) }
        }.execute()
        return result.toResponse()
    }

    override suspend fun postForm(url: String, form: Map<String, String>): Response {
        val result = client.submitForm<HttpStatement> {
            url {
                host = baseUrl
                path(listOf(url))
            }
            body = formData {
                form.forEach {
                    append(it.key, it.value)
                }
            }
        }.execute()
        return result.toResponse()
    }

    private suspend fun HttpResponse.toResponse(): Response {
        var string: String? = null

        while (true) {
            val line = content.readUTF8Line() ?: break
            string = string?.plus(line) ?: line
        }

        return Response(status.value, string, headers.names().map { Pair(it, headers[it]) }.toMap())
    }
}
