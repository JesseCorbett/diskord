package com.jessecorbett.diskord.internal

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal val httpClient = OkHttpClient.Builder()
        .cache(null)
        .connectionPool(ConnectionPool(1, 5, TimeUnit.SECONDS))
        .build()

//internal inline fun <reified T> Response.bodyAs(): T {
//    val bodyString = this.body()?.string() ?: throw DiscordCompatibilityException("Received a null body, but expected it to be present")
//    this.body()?.close()
//    return jsonMapper.readValue(bodyString, T::class.java)!!
//}
//
//internal inline fun <reified T> Response.bodyAsList(): List<T> {
//    val bodyString = this.body()?.string() ?: throw DiscordCompatibilityException("Received a null body, but expected it to be present")
//    this.body()?.close()
//    return jsonMapper.readValue(bodyString, jsonMapper.typeFactory.constructCollectionType(List::class.java, T::class.java))
//}
