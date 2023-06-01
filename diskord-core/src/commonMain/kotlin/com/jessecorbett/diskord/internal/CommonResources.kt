package com.jessecorbett.diskord.internal

import io.ktor.client.engine.*

internal const val defaultUserAgentUrl = "https://gitlab.com/jesselcorbett/diskord"
internal const val defaultUserAgentVersion = "4.1.0"

internal expect fun websocketClient(): HttpClientEngineFactory<HttpClientEngineConfig>
internal expect fun httpClient(): HttpClientEngineFactory<HttpClientEngineConfig>
