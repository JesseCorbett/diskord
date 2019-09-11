package com.jessecorbett.diskord.api.rest.client.internal

import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.http.content.PartData

internal const val DISCORD_API_URL = "https://discordapp.com/api"

@DiskordInternals
interface RestClient {
    /**
     * Make a GET request.
     *
     * @param url the url of the request
     * @param headers the headers to send with the request
     *
     * @return the HTTP response
     */
    suspend fun getRequest(url: String, headers: Map<String, String>): Response

    /**
     * Make a POST request.
     *
     * @param url the url of the request
     * @param jsonBody the body to send with the request
     * @param headers the headers to send with the request
     *
     * @return the HTTP response
     */
    suspend fun postRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response

    /**
     * Make a POST request with multipart data.
     *
     * @param url the url of the request
     * @param parts
     * @param headers the headers to send with the request
     *
     * @return the HTTP response
     */
    suspend fun postMultipartRequest(url: String, parts: List<PartData>, headers: Map<String, String>): Response

    /**
     * Make a PUT request.
     *
     * @param url the url of the request
     * @param jsonBody the body to send with the request
     * @param headers the headers to send with the request
     *
     * @return the HTTP response
     */
    suspend fun putRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response

    /**
     * Make a PATCH request.
     *
     * @param url the url of the request
     * @param jsonBody the body to send with the request
     * @param headers the headers to send with the request
     *
     * @return the HTTP response
     */
    suspend fun patchRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response

    /**
     * Make a DELETE request.
     *
     * @param url the url of the request
     * @param headers the headers to send with the request
     *
     * @return the HTTP response
     */
    suspend fun deleteRequest(url: String, headers: Map<String, String>): Response

    /**
     * Make a POST request with the specified form data.
     *
     * @param url the url of the request
     * @param form the form data to send with the request
     *
     * @return the HTTP response
     */
    suspend fun postForm(url: String, form: Map<String, String>): Response
}
