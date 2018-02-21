package com.jessecorbett.diskord

import com.fasterxml.jackson.module.kotlin.readValue
import com.jessecorbett.diskord.api.gateway.GatewayMessage
import com.jessecorbett.diskord.exception.DiscordCompatibilityException
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class DiscordWebSocketListener(private val acceptMessage: (GatewayMessage) -> Unit) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        if (response.code() == 101) return
        println(response)
        TODO("Handle other issues")
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
        println(closeCode)
        println(reason)
        TODO("Implement")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        val closeCode = WebSocketCloseCode.values().find { it.code == code } ?: throw DiscordCompatibilityException("Unexpected close code")
        println(code)
        println(reason)
        TODO("Implement")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        println(response)
        t.printStackTrace()
        TODO("Implement")
    }
}
