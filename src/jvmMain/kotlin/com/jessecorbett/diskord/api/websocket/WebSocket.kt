package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.websocket.model.GatewayMessage
import com.jessecorbett.diskord.internal.httpClient
import okhttp3.Request

actual class WebSocket actual constructor(url: String, token: String, acceptMessage: (GatewayMessage) -> Unit, lifecycleManager: WebSocketLifecycleManager) {
    private val websocket: okhttp3.WebSocket

    init {
        val request = Request.Builder()
            .url("$url?encoding=json&v=6")
            .addHeader("Authorization", "Bot $token")
            .build()

        websocket = httpClient.newWebSocket(request, DiscordWebSocketListener(acceptMessage, lifecycleManager))
    }

    actual fun close(closeCode: WebSocketCloseCode, reason: String?, forceClose: Boolean) {
        websocket.close(closeCode.code, reason)
        if (forceClose) {
            httpClient.dispatcher().executorService().shutdown()
        }
    }

    actual fun send(message: String) {
        websocket.send(message)
    }
}
