package com.jessecorbett.diskord.internal

import com.fasterxml.jackson.module.kotlin.readValue
import com.jessecorbett.diskord.DiscordLifecycleManager
import com.jessecorbett.diskord.api.gateway.GatewayMessage
import com.jessecorbett.diskord.api.gateway.WebSocketCloseCode
import com.jessecorbett.diskord.exception.DiscordCompatibilityException
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.slf4j.LoggerFactory

class DiscordWebSocketListener(
        private val acceptMessage: (GatewayMessage) -> Unit,
        private val lifecycleManager: DiscordLifecycleManager
) : WebSocketListener() {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        if (response.code() == 101) return
        logger.error("Encountered an unexpected error, code: ${response.body()}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val gatewayMessage = jsonMapper.readValue<GatewayMessage>(text)
        acceptMessage(gatewayMessage)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        TODO("This should never be called yet, we'll come back to it when it's time to implement ETF")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        val closeCode = WebSocketCloseCode.values().find { it.code == code } ?: throw DiscordCompatibilityException("Unexpected close code")
        lifecycleManager.closing(code, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        val closeCode = WebSocketCloseCode.values().find { it.code == code } ?: throw DiscordCompatibilityException("Unexpected close code")
        lifecycleManager.closed(code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        lifecycleManager.failed(t, response)
    }
}
