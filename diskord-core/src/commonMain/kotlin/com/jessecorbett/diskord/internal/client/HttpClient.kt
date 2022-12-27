package com.jessecorbett.diskord.internal.client

import com.jessecorbett.diskord.internal.httpClient
import com.jessecorbett.diskord.util.DEBUG_MODE
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val BOT_AUTH_PREFIX = "-> Authorization: Bot"

internal fun buildClient(json: Json) = HttpClient(httpClient()) {
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
            LogLevel.INFO
        }
    }
    install(ContentNegotiation) {
        json(json)
    }

    expectSuccess = false
}
