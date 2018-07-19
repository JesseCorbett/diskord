package com.jessecorbett.diskord.internal

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.jessecorbett.diskord.exception.DiscordCompatibilityException
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit

internal val httpClient = OkHttpClient.Builder()
        .cache(null)
        .connectionPool(ConnectionPool(1, 3, TimeUnit.SECONDS))
        .build()

internal val jsonMapper = ObjectMapper().findAndRegisterModules()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)!!

internal inline fun <reified T> Response.bodyAs(): T {
    val bodyString = this.body()?.string() ?: throw DiscordCompatibilityException("Received a null body, but expected it to be present")
    this.body()?.close()
    return jsonMapper.readValue(bodyString, T::class.java)!!
}

internal inline fun <reified T> Response.bodyAsList(): List<T> {
    val bodyString = this.body()?.string() ?: throw DiscordCompatibilityException("Received a null body, but expected it to be present")
    this.body()?.close()
    return jsonMapper.readValue(bodyString, jsonMapper.typeFactory.constructCollectionType(List::class.java, T::class.java))
}

internal const val defaultUserAgentUrl = "https://github.com/JesseCorbett/Diskord"
internal const val defaultUserAgentVersion = "0.0.7"
