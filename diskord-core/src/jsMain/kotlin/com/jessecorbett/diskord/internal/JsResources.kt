package com.jessecorbett.diskord.internal

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*

internal actual fun websocketClient(): HttpClientEngineFactory<HttpClientEngineConfig> = Js

internal actual fun httpClient(): HttpClientEngineFactory<HttpClientEngineConfig> = Js
