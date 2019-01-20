package com.jessecorbett.diskord.api.websocket

import com.fasterxml.jackson.module.kotlin.treeToValue
import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.exception.DiscordCompatibilityException
import com.jessecorbett.diskord.api.rest.client.DiscordClient
import com.jessecorbett.diskord.api.websocket.commands.Identify
import com.jessecorbett.diskord.api.websocket.commands.Resume
import com.jessecorbett.diskord.api.websocket.events.DiscordEvent
import com.jessecorbett.diskord.api.websocket.events.Hello
import com.jessecorbett.diskord.api.websocket.events.Ready
import com.jessecorbett.diskord.api.websocket.model.GatewayMessage
import com.jessecorbett.diskord.api.websocket.model.OpCode
import com.jessecorbett.diskord.internal.*
import kotlinx.coroutines.*
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import org.slf4j.LoggerFactory
import kotlin.coroutines.CoroutineContext

/**
 * Representation and container of a websocket connection to the discord gateway.
 *
 * @property token The user API token.
 * @property eventListener The event listener to call for gateway events.
 * @property sessionId The id of the session, null if this is a new connection.
 * @property sequenceNumber The gateway sequence number, initially null if this is a new connection.
 * @property shardId The id of this shard of the bot, 0 if this is the DM shard or the only shard.
 * @property userType The type of API user, assumed to be a bot.
 * @property websocketLifecycleListener Lifecycle hooks for the low level websocket connection.
 * @property eventListenerContext The coroutine context to run [EventListener] events in.
 * @property heartbeatContext The coroutine context to process heartbeat events to the gateway in.
 *
 * @constructor Provisions and connects a websocket connection for the user to discord.
 */
class DiscordWebSocket(
        private val token: String,
        private val eventListener: EventListener,
        autoStart: Boolean = true,
        var sessionId: String? = null,
        var sequenceNumber: Int? = null,
        private val shardId: Int = 0,
        private val shardCount: Int = 0,
        private val userType: DiscordUserType = DiscordUserType.BOT,
        private val websocketLifecycleListener: WebsocketLifecycleListener? = null,
        private val eventListenerContext: CoroutineContext = Dispatchers.Default,
        private val heartbeatContext: CoroutineContext = Dispatchers.Default
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private var socket: WebSocket? = null
    private var heartbeatJob: Job? = null

    init {
        if (autoStart) {
            startConnection()
        }
    }

    private fun startConnection() {
        GlobalScope.launch {
            val gatewayUrl = DiscordClient(token, userType).getBotGateway().url

            val request = Request.Builder()
                    .url("$gatewayUrl?encoding=json&v=6")
                    .addHeader("Authorization", "Bot $token")
                    .build()

            socket = httpClient.newWebSocket(request, DiscordWebSocketListener(::receiveMessage, object : WebsocketLifecycleManager {
                override fun start() {
                    websocketLifecycleListener?.started()
                }

                override fun closing(code: WebSocketCloseCode, reason: String) {
                    logger.info("Closing with code '$code' and reason '$reason'")
                    if (code != WebSocketCloseCode.NORMAL_CLOSURE)
                        restart()
                    websocketLifecycleListener?.closing(code, reason)
                }

                override fun closed(code: WebSocketCloseCode, reason: String) {
                    logger.info("Closed with code '$code' and reason '$reason'")
                    if (code != WebSocketCloseCode.NORMAL_CLOSURE)
                        restart()
                    websocketLifecycleListener?.closed(code, reason)
                }

                override fun failed(failure: Throwable, response: Response?) {
                    logger.error("Socket connection encountered an exception", failure)
                    restart()
                    websocketLifecycleListener?.failed(failure, response)
                }
            }))
        }
    }

    /**
     * Starts the websocket.
     *
     * Not technically different from [DiscordWebSocket.restart] in functionality if the bot isn't already running.
     */
    fun start() {
        startConnection()
    }

    /**
     * Shuts down the connection.
     *
     * @param forceClose Forces closed the connection. False by default. Only set to true if this is the only connection
     * in this program as it force closes the http client shared by all websocket and REST client instances.
     */
    fun close(forceClose: Boolean = false) {
        logger.debug("Closing")
        // Not sure if we want to join here or let it cancel async. Default safely to blocking behavior
        GlobalScope.launch { heartbeatJob?.cancelAndJoin() }
        heartbeatJob = null
        socket?.close(WebSocketCloseCode.NORMAL_CLOSURE.code, "Requested close")
        if (forceClose) {
            httpClient.dispatcher().executorService().shutdown()
        }
        logger.info("Closed connection")
    }

    /**
     * Restarts the connection.
     *
     * Maintains sessionId and sequenceNumber so events happening during restart and resumes.
     */
    fun restart() {
        logger.debug("Restarting")
        close()
        startConnection()
        logger.info("Restarted connection")
    }

    private fun receiveMessage(gatewayMessage: GatewayMessage) {
        logger.debug("Received OpCode ${gatewayMessage.opCode}")
        when (gatewayMessage.opCode) {
            OpCode.DISPATCH -> {
                sequenceNumber = gatewayMessage.sequenceNumber
                receiveGatewayMessage(gatewayMessage)
            }
            OpCode.HEARTBEAT -> {
                sendGatewayMessage(OpCode.HEARTBEAT_ACK)
            }
            OpCode.RECONNECT -> {
                logger.info("Server requested a reconnect")
                restart()
            }
            OpCode.INVALID_SESSION -> {
                logger.warn("The session was invalid, falling back to new session behavior")
                sessionId = null
                sequenceNumber = null
                restart()
            }
            OpCode.HELLO -> {
                 initializeSession(jsonMapper.treeToValue(gatewayMessage.dataPayload!!))
            }
            OpCode.HEARTBEAT_ACK -> {
                // TODO: We should handle errors to do with a lack of heartbeat ack, possibly restart. Low priority.
            }
            else -> {
                throw DiscordCompatibilityException("Reached unreachable OpCode: ${gatewayMessage.opCode.name} (${gatewayMessage.opCode.code})")
            }
        }
    }

    private fun initializeSession(hello: Hello) {
        if (sessionId != null && sequenceNumber != null) {
            sendGatewayMessage(OpCode.RESUME, Resume(token, sessionId!!, sequenceNumber!!))
        } else {
            val identify = if (shardCount > 0) {
                Identify(token, listOf(shardId, shardCount))
            } else {
                Identify(token)
            }
            sendGatewayMessage(OpCode.IDENTIFY, identify)
        }

        heartbeatJob?.cancel()

        heartbeatJob = GlobalScope.launch(heartbeatContext) {
            while (this.isActive) {
                sendGatewayMessage(OpCode.HEARTBEAT, sequenceNumber)
                delay(hello.heartbeatInterval)
            }
        }
    }

    private fun receiveGatewayMessage(gatewayMessage: GatewayMessage) {
        gatewayMessage.dataPayload ?: throw DiscordCompatibilityException("Encountered DiscordEvent ${gatewayMessage.event} without event data")

        val discordEvent = DiscordEvent.values().find { it.name == gatewayMessage.event } ?: return // Ignore unknown events, since we receive non-bot events because I guess it's hard for discord to not send bots non-bot events

        if (discordEvent == DiscordEvent.READY) {
            sessionId = jsonMapper.treeToValue<Ready>(gatewayMessage.dataPayload).sessionId
        }

        GlobalScope.launch(eventListenerContext) {
            dispatchEvent(eventListener, discordEvent, gatewayMessage.dataPayload)
        }
    }

    private fun sendGatewayMessage(opCode: OpCode, data: Any? = null, event: DiscordEvent? = null) {
        logger.debug("Sending OpCode: $opCode")
        val eventName = event?.name ?: ""
        socket?.send(jsonMapper.writeValueAsString(GatewayMessage(opCode, jsonMapper.valueToTree(data), sequenceNumber, eventName)))
    }
}
