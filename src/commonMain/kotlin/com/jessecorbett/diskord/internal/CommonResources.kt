package com.jessecorbett.diskord.internal

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

internal const val defaultUserAgentUrl = "https://gitlab.com/jesselcorbett/diskord"
internal const val defaultUserAgentVersion = "1.5.1"

internal expect fun websocketClient(): HttpClientEngineFactory<HttpClientEngineConfig>
internal expect fun httpClient(): HttpClientEngineFactory<HttpClientEngineConfig>
