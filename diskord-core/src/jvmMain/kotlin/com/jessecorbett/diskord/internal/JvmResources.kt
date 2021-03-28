package com.jessecorbett.diskord.internal

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*

internal actual fun websocketClient(): HttpClientEngineFactory<HttpClientEngineConfig> = CIO

internal actual fun httpClient(): HttpClientEngineFactory<HttpClientEngineConfig> = CIO
