package com.jessecorbett.diskord.internal

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.*
import io.ktor.util.KtorExperimentalAPI

@OptIn(KtorExperimentalAPI::class)
internal actual fun websocketClient(): HttpClientEngineFactory<HttpClientEngineConfig> = Js

@OptIn(KtorExperimentalAPI::class)
internal actual fun httpClient(): HttpClientEngineFactory<HttpClientEngineConfig> = Js
