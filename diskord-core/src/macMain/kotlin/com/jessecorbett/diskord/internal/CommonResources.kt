package com.jessecorbett.diskord.internal

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

internal actual fun websocketClient(): HttpClientEngineFactory<HttpClientEngineConfig> = Darwin
internal actual fun httpClient(): HttpClientEngineFactory<HttpClientEngineConfig> = Darwin
