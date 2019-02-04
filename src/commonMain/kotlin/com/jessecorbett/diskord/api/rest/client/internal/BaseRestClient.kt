package com.jessecorbett.diskord.api.rest.client.internal

const val discordApi = "https://discordapp.com/api"

expect open class BaseRestClient() {
    protected suspend fun getRequest(url: String, headers: Map<String, String>): Response

    protected suspend fun postRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response

    protected suspend fun putRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response

    protected suspend fun patchRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response

    protected suspend fun deleteRequest(url: String, headers: Map<String, String>): Response

    protected suspend fun postForm(url: String, form: Map<String, String>): Response
}
