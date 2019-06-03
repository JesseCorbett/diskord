package com.jessecorbett.diskord.api.rest.client

import com.jessecorbett.diskord.api.rest.client.internal.Response

interface RestClient {
    suspend fun getRequest(url: String, headers: Map<String, String>): Response

    suspend fun postRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response

    suspend fun putRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response

    suspend fun patchRequest(url: String, jsonBody: String?, headers: Map<String, String>): Response

    suspend fun deleteRequest(url: String, headers: Map<String, String>): Response

    suspend fun postForm(url: String, form: Map<String, String>): Response
}
