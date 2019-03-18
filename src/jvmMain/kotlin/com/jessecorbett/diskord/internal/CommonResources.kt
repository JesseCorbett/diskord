package com.jessecorbett.diskord.internal

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp

actual fun websocketClient(): HttpClientEngineFactory<HttpClientEngineConfig> = CIO
actual fun httpClient(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
