package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.websocket.model.GatewayMessage
import com.jessecorbett.diskord.internal.httpClient
import okhttp3.Request
import okhttp3.WebSocket

actual class WebSocket {
    private var socket: WebSocket? = null

    actual fun start(url: String, token: String, lifecycleManager: WebsocketLifecycleManager, receiveMessage: (GatewayMessage) -> Unit) {
        val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot $token")
                .build()

        socket = httpClient.newWebSocket(request, DiscordWebSocketListener(receiveMessage, lifecycleManager))
    }

    actual fun close(closeCode: WebSocketCloseCode, reason: String?, force: Boolean) {

    }

    actual fun sendMessage(message: String) {

    }
}
