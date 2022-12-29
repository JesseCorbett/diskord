package com.jessecorbett.diskord.internal

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttp

internal actual fun websocketClient(): HttpClientEngineFactory<HttpClientEngineConfig> = WinHttp
internal actual fun httpClient(): HttpClientEngineFactory<HttpClientEngineConfig> = WinHttp
