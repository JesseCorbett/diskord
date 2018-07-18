package com.jessecorbett.diskord

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.treeToValue
import com.jessecorbett.diskord.api.gateway.GatewayMessage
import com.jessecorbett.diskord.api.gateway.OpCode
import com.jessecorbett.diskord.api.gateway.commands.Identify
import com.jessecorbett.diskord.api.gateway.commands.Resume
import com.jessecorbett.diskord.api.gateway.events.DiscordEvent
import com.jessecorbett.diskord.api.gateway.events.Hello
import com.jessecorbett.diskord.api.gateway.events.Ready
import com.jessecorbett.diskord.exception.DiscordCompatibilityException
import com.jessecorbett.diskord.internal.*
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.Request
import okhttp3.WebSocket
import org.slf4j.LoggerFactory

class DiscordWebSocket(
        val token: String,
        val eventListener: EventListener,
        var sessionId: String? = null,
        var sequenceNumber: Int? = null,
        val shardId: Int = 0,
        val shardCount: Int = 0,
        private val heartbeatManager: HeartbeatManager = DefaultHeartbeatManager(),
        private val lifecycleManager: DiscordLifecycleManager = DefaultLifecycleManager()
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private var socket: WebSocket
    private val gatewayUrl = runBlocking { DiscordClient(DiscordToken(token, TokenType.BOT)).getBotGateway().url }

    init {
        lifecycleManager.start(::restart)
        socket = startConnection()
    }

    private fun startConnection(): WebSocket {
        val request = Request.Builder()
                .url("$gatewayUrl?encoding=json&v=6")
                .addHeader("Authorization", "Bot $token")
                .build()

        return httpClient.newWebSocket(request, DiscordWebSocketListener(::receiveMessage, lifecycleManager))
    }

    fun close() {
        logger.info("Closing")
        heartbeatManager.close()
        socket.close(0, "Requested close")
        logger.info("Closed")
    }

    private fun restart() {
        logger.info("Restarting")
        socket = startConnection()
        logger.info("Restarted")
    }

    private fun receiveEvent(gatewayMessage: GatewayMessage) {
        gatewayMessage.dataPayload ?: throw DiscordCompatibilityException("Encountered DiscordEvent ${gatewayMessage.event} without event data")
        val discordEvent = DiscordEvent.values().find { it.name == gatewayMessage.event } ?: return // Ignore unknown events
        if (discordEvent == DiscordEvent.READY) {
            setSessionId(gatewayMessage.dataPayload)
        }
        launch(eventListener.context) {
            dispatchEvent(eventListener, discordEvent, gatewayMessage.dataPayload)
        }
    }

    private fun setSessionId(data: JsonNode) {
        val readyData = jsonMapper.treeToValue<Ready>(data)
        sessionId = readyData.sessionId
    }

    private fun receiveMessage(gatewayMessage: GatewayMessage) {
        logger.debug("Received OpCode ${gatewayMessage.opCode}")
        when (gatewayMessage.opCode) {
            OpCode.DISPATCH -> {
                sequenceNumber = gatewayMessage.sequenceNumber
                receiveEvent(gatewayMessage)
            }
            OpCode.HEARTBEAT -> {
                heartbeatManager.acceptHeartbeat(gatewayMessage)
            }
            OpCode.IDENTIFY -> {
                // Only here for completeness, will never receive
                throw DiscordCompatibilityException("Reached unreachable Identify code")
            }
            OpCode.STATUS_UPDATE -> {
                // Only here for completeness, will never receive
                throw DiscordCompatibilityException("Reached unreachable Status Update code")
            }
            OpCode.VOICE_STATE_UPDATE -> {
                // Only here for completeness, will never receive
                throw DiscordCompatibilityException("Reached unreachable Voice State Update code")
            }
            OpCode.VOICE_SERVER_PING -> {
                // Only here for completeness, will never receive
                throw DiscordCompatibilityException("Reached unreachable Voice Server Ping code")
            }
            OpCode.RESUME -> {
                // Only here for completeness, will never receive
                throw DiscordCompatibilityException("Reached unreachable Resume code")
            }
            OpCode.RECONNECT -> {
                restart()
            }
            OpCode.REQUEST_GUILD_MEMBERS -> {
                // Only here for completeness, will never receive
                throw DiscordCompatibilityException("Reached unreachable Request Guild Member code")
            }
            OpCode.INVALID_SESSION -> {
                sessionId = null
                sequenceNumber = null
                restart()
            }
            OpCode.HELLO -> {
                val hello = jsonMapper.treeToValue<Hello>(gatewayMessage.dataPayload!!)
                initialize()
                heartbeatManager.start(hello.heartbeatInterval, ::sendHeartbeat, ::sendHeartbeatAcknowledgement)
            }
            OpCode.HEARTBEAT_ACK -> {
                heartbeatManager.acceptAcknowledgement(gatewayMessage)
            }
        }
    }

    private fun sendHeartbeat() {
        sendGatewayMessage(OpCode.HEARTBEAT, sequenceNumber)
    }

    private fun sendHeartbeatAcknowledgement() {
        sendGatewayMessage(OpCode.HEARTBEAT_ACK)
    }

    private fun initialize() {
        if (sessionId != null && sequenceNumber != null) {
            sendGatewayMessage(OpCode.RESUME, Resume(token, sessionId!!, sequenceNumber!!))
        } else {
            val identify = if (shardCount > 0) {
                Identify(token, arrayOf(shardId, shardCount))
            } else {
                Identify(token)
            }
            sendGatewayMessage(OpCode.IDENTIFY, identify)
        }
    }

    private fun sendGatewayMessage(opCode: OpCode, data: Any? = null, event: DiscordEvent? = null) {
        logger.debug("Sending OpCode: $opCode")
        val eventName = event?.name ?: ""
        socket.send(jsonMapper.writeValueAsString(GatewayMessage(opCode, jsonMapper.valueToTree(data), sequenceNumber, eventName)))
    }
}
