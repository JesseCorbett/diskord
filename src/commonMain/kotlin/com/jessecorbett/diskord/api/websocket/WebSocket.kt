package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.websocket.model.GatewayMessage

expect class WebSocket(url: String, token: String, acceptMessage: (GatewayMessage) -> Unit, lifecycleManager: WebSocketLifecycleManager) {
    fun close(closeCode: WebSocketCloseCode, reason: String? = null, forceClose: Boolean = false)

    fun send(message: String)
}
