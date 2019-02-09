package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.rest.client.internal.Response

interface WebSocketLifecycleManager {
    fun start()

    fun closing(code: WebSocketCloseCode, reason: String)

    fun closed(code: WebSocketCloseCode, reason: String)

    fun failed(failure: Throwable, response: Response?)
}
