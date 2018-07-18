package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.gateway.WebSocketCloseCode
import okhttp3.Response

interface DiscordLifecycleManager {
    fun start(restart: () -> Unit)

    fun closing(code: WebSocketCloseCode, reason: String)

    fun closed(code: WebSocketCloseCode, reason: String)

    fun failed(failure: Throwable, response: Response?)
}
