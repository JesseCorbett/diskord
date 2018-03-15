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
import kotlin.reflect.KClass

internal val httpClient = OkHttpClient.Builder()
        .cache(null)
        .connectionPool(ConnectionPool(1, 3, TimeUnit.SECONDS)).build()

internal val jsonMapper = ObjectMapper().findAndRegisterModules()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)!!

internal fun <T: Any> Response.bodyAs(bodyClass: KClass<T>): T {
    val bodyString = this.body()?.string() ?: throw DiscordCompatibilityException("Received a null body, but expected it to be present")
    this.body()?.close()
    return jsonMapper.readValue(bodyString, bodyClass.java)!!
}

internal fun <T: Any> Response.bodyAsListOf(bodyClass: KClass<T>): List<T> {
    val bodyString = this.body()?.string() ?: throw DiscordCompatibilityException("Received a null body, but expected it to be present")
    this.body()?.close()
    return jsonMapper.readValue(bodyString, jsonMapper.typeFactory.constructCollectionType(List::class.java, bodyClass.java))
}

internal const val defaultUserAgentUrl = "https://github.com/JesseCorbett/Diskord"
internal const val defaultUserAgentVersion = "0.0.0"
