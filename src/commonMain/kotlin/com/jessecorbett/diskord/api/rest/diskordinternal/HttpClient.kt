package com.jessecorbett.diskord.api.rest.diskordinternal

import com.jessecorbett.diskord.internal.httpClient
import com.jessecorbett.diskord.util.DEBUG_MODE
import com.jessecorbett.diskord.util.defaultJson
import com.jessecorbett.diskord.util.omitNullsJson
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*


private const val BOT_AUTH_PREFIX = "-> Authorization: Bot"

internal fun buildClient(omitNulls: Boolean = false) = HttpClient(httpClient()) {
    install(Logging) {
        logger = object : Logger {
            private val delegate = Logger.DEFAULT
            override fun log(message: String) {
                delegate.log(
                    if (!DEBUG_MODE && message.startsWith(BOT_AUTH_PREFIX)) {
                        "$BOT_AUTH_PREFIX <token hidden>"
                    } else {
                        message
                    }
                )
            }
        }
        level = if (DEBUG_MODE) {
            LogLevel.ALL
        } else {
            LogLevel.HEADERS
        }
    }
    install(JsonFeature) {
        serializer = if (omitNulls) {
            KotlinxSerializer(omitNullsJson)
        } else {
            KotlinxSerializer(defaultJson)
        }
    }

    expectSuccess = false
}
