package com.jessecorbett.diskord.api.rest.client.internal

import com.jessecorbett.diskord.api.exception.DiscordRateLimitException
import com.jessecorbett.diskord.internal.httpClient
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import okhttp3.Response as OkResponse

actual open class BaseRestClient {

    protected actual suspend fun getRequest(url: String, headers: Map<String, String>) = request(commonRequest(url, headers).get().build())

    protected actual suspend fun postRequest(url: String, jsonBody: String?, headers: Map<String, String>) = request(commonRequest(url, headers).post(jsonBody).build())

    protected actual suspend fun putRequest(url: String, jsonBody: String?, headers: Map<String, String>) = request(commonRequest(url, headers).put(jsonBody).build())

    protected actual suspend fun patchRequest(url: String, jsonBody: String?, headers: Map<String, String>) = request(commonRequest(url, headers).patch(jsonBody).build())

    protected actual suspend fun deleteRequest(url: String, headers: Map<String, String>) = request(commonRequest(url, headers).delete().build())


    protected actual suspend fun postForm(url: String, form: Map<String, String>): Response {
        val body = FormBody.Builder().apply {
            form.forEach { (key, value) ->
                add(key, value)
            }
        }.build()

        return request(commonRequest(url, emptyMap()).post(body).build())
    }


    private fun Request.Builder.post(jsonBody: String?) = this.post(jsonBody(jsonBody))
    private fun Request.Builder.put(jsonBody: String?) = this.put(jsonBody(jsonBody))
    private fun Request.Builder.patch(jsonBody: String?) = this.patch(jsonBody(jsonBody))

    private fun jsonBody(jsonBody: String?) = RequestBody.create(MediaType.get("application/json; charset=utf-8"), jsonBody ?: "")

    private suspend fun request(request: Request): Response {
        val response = makeRequest(request)
        val responseHeaders = response.headers().toMultimap().keys.map { Pair(it, response.header(it)) }.toMap()

        return Response(response.code(), response.body()?.string(), responseHeaders)
    }

    private fun commonRequest(url: String, headers: Map<String, String>) =
            Request.Builder().url(discordApi + url).apply {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }

    private suspend fun makeRequest(request: Request): OkResponse = try {
        suspendCoroutine { cont ->
            httpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, exception: IOException) {
                    cont.resumeWithException(exception)
                }

                override fun onResponse(call: Call, response: OkResponse) {
                    cont.resume(response)
                }
            })
        }
    } catch (rateLimitException: DiscordRateLimitException) {
        makeRequest(request)
    }
}
