package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.util.DEBUG_MODE
import mu.KotlinLogging
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

private const val BOT_AUTH_PREFIX = "Authorization: Bot"

internal val httpClient = OkHttpClient.Builder()
    .cache(null)
    .connectionPool(ConnectionPool(1, 5, TimeUnit.SECONDS))
    .apply {
        try {
            Class.forName("okhttp3.logging.HttpLoggingInterceptor")

            val httpLogger = KotlinLogging.logger("com.jessecorbett.diskord.internal.HttpLoggingInterceptor")

            addInterceptor(HttpLoggingInterceptor { message ->
                httpLogger.trace(if (message.startsWith(BOT_AUTH_PREFIX) && !DEBUG_MODE) {
                    "$BOT_AUTH_PREFIX <token hidden>"
                } else {
                    message
                })
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        } catch (error: ClassNotFoundException) {
            logger.debug { "HttpLoggingInterceptor was not found on the classpath.  Raw HTTP messages will not be logged." }
        }
    }
    .build()
