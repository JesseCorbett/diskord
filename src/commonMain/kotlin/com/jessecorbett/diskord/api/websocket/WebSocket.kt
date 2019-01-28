package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.websocket.model.GatewayMessage

expect class WebSocket() {
    fun start(url: String, token: String, lifecycleManager: WebsocketLifecycleManager, receiveMessage: (GatewayMessage) -> Unit)

    fun close(closeCode: WebSocketCloseCode, reason: String? = null, force: Boolean = false)

    fun sendMessage(message: String)
}
