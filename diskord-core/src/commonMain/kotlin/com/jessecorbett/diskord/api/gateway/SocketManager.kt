package com.jessecorbett.diskord.api.gateway

import com.jessecorbett.diskord.api.gateway.model.GatewayMessage
import com.jessecorbett.diskord.internal.websocketClient
import com.jessecorbett.diskord.util.DEBUG_MODE
import com.jessecorbett.diskord.util.StripBlankSWSEHeader
import com.jessecorbett.diskord.util.defaultJson
import com.jessecorbett.diskord.util.toHexDump
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString

internal class SocketManager(url: String, private val emitMessage: suspend (GatewayMessage) -> Unit) {
    private val logger = KotlinLogging.logger {}

    private var socketClient: HttpClient = buildWSClient()
    private var url = if (url.startsWith("wss://")) {
        url.drop(6)
    } else {
        url
    }

    private var session: WebSocketSession? = null
    private val outgoingMessages: Channel<GatewayMessage> = Channel()
    var running: Boolean = false
        private set

    val alive: Boolean
        get() = running && session != null

    suspend fun open() {
        running = true
        while (running) {
            initializeNewConnection()
        }
    }

    suspend fun close() {
        running = false
        session?.close(CloseReason(WebSocketCloseCode.NORMAL_CLOSURE.code, "The bot requested the connection close"))
        socketClient.close()
    }

    suspend fun restartConnection() {
        session?.close(CloseReason(WebSocketCloseCode.NORMAL_CLOSURE.code, "The bot requested the connection close in order to restart"))
    }

    suspend fun send(gatewayMessage: GatewayMessage) {
        outgoingMessages.send(gatewayMessage)
    }

    private suspend fun initializeNewConnection() = try {
            coroutineScope {
                socketClient.wss(host = url, port = 443, request = {
                    this.url.parameters["v"] = "9"
                    this.url.parameters["encoding"] = "json"
                    logger.trace { "Building a socket HttpRequest" }
                }) {
                    logger.debug { "Starting a new websocket connection" }

                    session = this

                    logger.debug { "Starting incoming loop" }

                    launch {
                        for (message in outgoingMessages) {
                            send(defaultJson.encodeToString(message))
                        }
                    }

                    for (frame in incoming) {
                        logger.trace { "Incoming Message:\nInfo:\n\tframe = $frame\nData:\n${frame.data.toHexDump(rowPrefix = "\t")}" }

                        when (frame) {
                            is Frame.Text -> emitMessage(defaultJson.decodeFromString(GatewayMessage.serializer(), frame.readText()))
                            is Frame.Binary -> TODO("Add support for binary formatted data")
                            is Frame.Close -> logger.info { "Close Frame sent with message: $frame" }
                            is Frame.Ping, is Frame.Pong -> logger.debug { frame }
                            else -> logger.error { "Encountered unexpected frame type " + frame::class.simpleName }
                        }
                    }
                    logger.info { "Exited the incoming loop" }

                    val closeReason = this@wss.closeReason.await()
                    if (closeReason == null) {
                        logger.warn { "Closed with no close reason, probably a connection issue" }
                    } else {
                        val closeCode = WebSocketCloseCode.values().find { it.code == closeReason.code }
                        val message = if (closeReason.message.isEmpty()) {
                            "Closed with code '$closeCode' with no reason provided"
                        } else {
                            "Closed with code '$closeCode' with the reason '${closeReason.message}'"
                        }
                        logger.info { message }
                    }
                    this.cancel()
                }
            }
        } finally {
            if (running) {
                logger.info { "Socket connection has closed but will restart momentarily" }
            } else {
                logger.info { "Socket connection has closed" }
            }
            session?.cancel()
            session = null
    }

    private fun buildWSClient(): HttpClient {
        return HttpClient(websocketClient()).config {
            install(WebSockets)
            install(StripBlankSWSEHeader)

            if (DEBUG_MODE) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }
        }
    }
}
