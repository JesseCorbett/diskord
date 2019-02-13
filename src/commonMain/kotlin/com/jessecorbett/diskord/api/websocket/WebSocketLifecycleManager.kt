package com.jessecorbett.diskord.api.websocket

interface WebsocketLifecycleManager {
    fun start()

    fun closing(code: WebSocketCloseCode, reason: String)

    fun closed(code: WebSocketCloseCode, reason: String)

    fun failed(failure: Throwable, code: Int?, body: String?)
}
