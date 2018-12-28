package com.jessecorbett.diskord.api.websocket

import com.fasterxml.jackson.module.kotlin.readValue
import com.jessecorbett.diskord.api.exception.DiscordCompatibilityException
import com.jessecorbett.diskord.api.websocket.model.GatewayMessage
import com.jessecorbett.diskord.internal.jsonMapper
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.slf4j.LoggerFactory

internal class DiscordWebSocketListener(
        private val acceptMessage: (GatewayMessage) -> Unit,
        private val lifecycleManager: WebsocketLifecycleManager
) : WebSocketListener() {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        if (response.code() == 101) return
        logger.error("Encountered an unexpected error, code: ${response.body()}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) = acceptMessage(jsonMapper.readValue(text))

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        TODO("This should never be called, we'll need it though if we choose to implement ETF")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        val closeCode = WebSocketCloseCode.values().find { it.code == code } ?: throw DiscordCompatibilityException("Unexpected close code")
        lifecycleManager.closing(closeCode, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        val closeCode = WebSocketCloseCode.values().find { it.code == code } ?: throw DiscordCompatibilityException("Unexpected close code")
        lifecycleManager.closed(closeCode, reason)
    }

    override fun onFailure(webSocket: WebSocket, throwable: Throwable, response: Response?) {
        lifecycleManager.failed(throwable, response)
    }
}

internal interface WebsocketLifecycleManager {
    fun start()

    fun closing(code: WebSocketCloseCode, reason: String)

    fun closed(code: WebSocketCloseCode, reason: String)

    fun failed(failure: Throwable, response: Response?)
}
