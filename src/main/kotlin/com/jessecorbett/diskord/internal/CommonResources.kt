package com.jessecorbett.diskord.internal

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

internal val httpClient = OkHttpClient.Builder()
        .cache(null)
        .connectionPool(ConnectionPool(1, 1, TimeUnit.MINUTES)).build()

internal val jsonMapper = ObjectMapper().findAndRegisterModules()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)!!

internal fun <T: Any> Response.bodyAs(bodyClass: KClass<T>) = jsonMapper.readValue(this.body()!!.string(), bodyClass.java)!!

internal fun <T: Any> Response.bodyAsListOf(bodyClass: KClass<T>): List<T>
        = jsonMapper.readValue(this.body()!!.string(), jsonMapper.typeFactory.constructCollectionType(List::class.java, bodyClass.java))
