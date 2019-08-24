package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.util.DEBUG_MODE
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.util.KtorExperimentalAPI
import mu.KotlinLogging
import okhttp3.logging.HttpLoggingInterceptor

private const val BOT_AUTH_PREFIX = "Authorization: Bot"

private val httpLogger = KotlinLogging.logger("com.jessecorbett.diskord.internal.HttpLoggingInterceptor")

@UseExperimental(KtorExperimentalAPI::class)
internal actual fun websocketClient(): HttpClientEngineFactory<HttpClientEngineConfig> = CIO
internal actual fun httpClient(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp

@Suppress("UNCHECKED_CAST")
internal actual fun configureHttpClient(): HttpClientConfig<HttpClientEngineConfig>.() -> Unit = {
    this as HttpClientConfig<OkHttpConfig>

    engine {

        addInterceptor(HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    httpLogger.trace(if (message.startsWith(BOT_AUTH_PREFIX) && !DEBUG_MODE) {
                        "$BOT_AUTH_PREFIX <token hidden>"
                    } else {
                        message
                    })
                }
            }
        ).apply { level = HttpLoggingInterceptor.Level.BODY })
    }
}
