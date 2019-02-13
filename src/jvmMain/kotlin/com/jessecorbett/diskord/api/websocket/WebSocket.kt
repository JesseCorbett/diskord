package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.websocket.model.GatewayMessage
import com.jessecorbett.diskord.internal.httpClient
import okhttp3.Request
import okhttp3.WebSocket

actual class WebSocket actual constructor(url: String, token: String, lifecycleManager: WebsocketLifecycleManager, receiveMessage: (GatewayMessage) -> Unit) {
    private var socket: WebSocket

    init {
        val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot $token")
                .build()

        socket = httpClient.newWebSocket(request, DiscordWebSocketListener(receiveMessage, lifecycleManager))
    }

    actual fun close(closeCode: WebSocketCloseCode, reason: String?, force: Boolean) {
        socket.close(closeCode.code, reason)
        if (force) {
            httpClient.dispatcher().executorService().shutdown()
        }
    }

    actual fun sendMessage(message: String) {
        socket.send(message)
    }
}
