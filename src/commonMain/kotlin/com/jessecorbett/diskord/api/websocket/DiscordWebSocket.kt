package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.exception.DiscordCompatibilityException
import com.jessecorbett.diskord.api.model.UserStatus
import com.jessecorbett.diskord.api.rest.client.DiscordClient
import com.jessecorbett.diskord.api.websocket.commands.Identify
import com.jessecorbett.diskord.api.websocket.commands.IdentifyShard
import com.jessecorbett.diskord.api.websocket.commands.Resume
import com.jessecorbett.diskord.api.websocket.commands.UpdateStatus
import com.jessecorbett.diskord.api.websocket.events.DiscordEvent
import com.jessecorbett.diskord.api.websocket.events.Hello
import com.jessecorbett.diskord.api.websocket.events.Ready
import com.jessecorbett.diskord.api.websocket.model.GatewayMessage
import com.jessecorbett.diskord.api.websocket.model.OpCode
import com.jessecorbett.diskord.api.websocket.model.UserStatusActivity
import com.jessecorbett.diskord.internal.websocketClient
import com.jessecorbett.diskord.util.DEBUG_MODE
import com.jessecorbett.diskord.util.toHexDump
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.wss
import io.ktor.http.cio.websocket.*
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import mu.KotlinLogging
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
 * @property eventListenerContext The coroutine context to run [EventListener] events in.
 * @param heartbeatContext The coroutine context to process heartbeat events to the gateway in.
 * @param httpClient The http client to use to create the websocket connection.
 * @property gatewayUrl The url to connect to. Will be fetched it not provided.
 *
 * @constructor Provisions and connects a websocket connection for the user to discord.
 */
@UseExperimental(UnstableDefault::class, KtorExperimentalAPI::class, ExperimentalCoroutinesApi::class)
class DiscordWebSocket(
    private val token: String,
    private val eventListener: EventListener,
    private var sessionId: String? = null,
    private var sequenceNumber: Int? = null,
    private val shardId: Int = 0,
    private val shardCount: Int = 0,
    private val userType: DiscordUserType = DiscordUserType.BOT,
    private val eventListenerContext: CoroutineContext = Dispatchers.Default,
    heartbeatContext: CoroutineContext = Dispatchers.Default,
    httpClient: HttpClientEngineFactory<HttpClientEngineConfig> = websocketClient(),
    private var gatewayUrl: String? = null
) {
    private val logger = KotlinLogging.logger {}
    private val socketClient: HttpClient = HttpClient(httpClient).config {
        install(WebSockets)
        if (DEBUG_MODE) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    private val eventListenerScope = CoroutineScope(eventListenerContext)
    private val heartbeatScope = CoroutineScope(heartbeatContext)

    private var heartbeatJob: Job? = null
    private var sendWebsocketMessage: (suspend (String) -> Unit)? = null
    private var stop: suspend (WebSocketCloseCode, String) -> Unit = { _, _ -> }

    private var expectedOpen = false
    private var isOpen = false

    private suspend fun initializeConnection() {
        val url = gatewayUrl ?: DiscordClient(token, userType).getBotGateway().let {
            logger.debug { it }
            it.url.removePrefix("wss://")
        }
        gatewayUrl = url

        isOpen = true

        logger.trace { "Attempting a websocket connection" }
        socketClient.wss(host = url, port = 443, request = {
            logger.trace { "Building socket HttpRequest" }
        }) {
            logger.info { "Starting socket connection" }

            sendWebsocketMessage = this::send
            stop = { code, reason -> close(CloseReason(code.code, reason)) }

            launch {
                val closeReason = this@wss.closeReason.await()
                if (closeReason == null) {
                    logger.warn { "Closed with no close reason, probably a connection issue" }
                } else {
                    val closeCode = WebSocketCloseCode.values().find { it.code == closeReason.code }
                    val message = if (closeReason.message.isEmpty()) {
                        "Closed with code '$closeCode' with no reason provided"
                    } else {
                        "Closed with code '$closeCode' for reason '${closeReason.message}'"
                    }
                    logger.warn { message }
                }
            }

            logger.info { "Starting incoming loop" }

            while (!incoming.isClosedForReceive) {
                val message = try {
                    incoming.receive()
                } catch (e: ClosedReceiveChannelException) {
                    logger.warn { "Receive channel is closed" }
                    break
                }

                logger.trace { "Incoming Message:\n${message.data.toHexDump()}" }

                when (message) {
                    is Frame.Text -> {
                        val text = message.readText()
                        receiveMessage(Json.nonstrict.parse(GatewayMessage.serializer(), text))
                    }
                    is Frame.Binary -> {
                        TODO("Add support for binary formatted data")
                    }
                    is Frame.Close -> {
                        logger.info { "Closing with message: $message" }
                    }
                    is Frame.Ping, is Frame.Pong -> {
                        // Not used
                        logger.debug { message }
                    }
                }
            }
            logger.info { "Exited the incoming loop" }

        }

        isOpen = false
        logger.info { "Socket connection has closed" }
    }

    /**
     * Starts the websocket.
     */
    suspend fun start() {
        expectedOpen = true
        while (expectedOpen) initializeConnection()
    }

    /**
     * Shuts down the connection.
     */
    suspend fun close() {
        logger.debug { "Closing connection" }
        expectedOpen = false
        heartbeatJob?.cancel()
        heartbeatJob = null
        stop(WebSocketCloseCode.NORMAL_CLOSURE, "Requested close")
        while (isOpen) {} // Block until the connection is confirmed closed, handling race conditions
        logger.info { "Closed connection" }
    }

    /**
     * Restarts the connection.
     *
     * Maintains sessionId and sequenceNumber so events happening during restart and resumes.
     */
    suspend fun restart() {
        logger.debug { "Restarting connection" }
        close()
        start()
        logger.info { "Restarted connection" }
    }

    /**
     * Sets the user status in Discord.
     *
     * @param status The user status to set to.
     * @param isAfk If the user is AFK.
     * @param idleTime How long the user has been idle, in milliseconds.
     * @param activity The activity, if any, that the user is performing.
     */
    suspend fun setStatus(status: UserStatus, isAfk: Boolean = false, idleTime: Int? = null, activity: UserStatusActivity? = null) {
        sendGatewayMessage(OpCode.STATUS_UPDATE, UpdateStatus(idleTime, activity, status, isAfk), UpdateStatus.serializer())
    }

    private suspend fun receiveMessage(gatewayMessage: GatewayMessage) {
        logger.trace { "Received OpCode ${gatewayMessage.opCode}" }
        when (gatewayMessage.opCode) {
            OpCode.DISPATCH -> {
                sequenceNumber = gatewayMessage.sequenceNumber
                receiveGatewayMessage(gatewayMessage)
            }
            OpCode.HEARTBEAT -> {
                sendGatewayMessage(OpCode.HEARTBEAT_ACK)
            }
            OpCode.RECONNECT -> {
                logger.info { "Server requested a reconnect" }
                restart()
            }
            OpCode.INVALID_SESSION -> {
                logger.warn { "The session was invalid, falling back to new session behavior" }
                sessionId = null
                sequenceNumber = null
                restart()
            }
            OpCode.HELLO -> {
                initializeSession(Json.nonstrict.fromJson(Hello.serializer(), gatewayMessage.dataPayload!!))
            }
            OpCode.HEARTBEAT_ACK -> {
                // TODO: We should handle errors to do with a lack of heartbeat ack, possibly restart.
                // Additional note, I've not observed Discord actually sending heartbeats
            }
            else -> {
                throw DiscordCompatibilityException("Reached unreachable OpCode: ${gatewayMessage.opCode.name} (${gatewayMessage.opCode.code})")
            }
        }
    }

    private suspend fun initializeSession(hello: Hello) {
        if (sessionId != null && sequenceNumber != null) { // RESUME
            // Compiler doesn't understand 2 != null's so we have to assert they're non-null
            sendGatewayMessage(OpCode.RESUME, Resume(token, sessionId!!, sequenceNumber!!), Resume.serializer())
        } else if (shardCount > 0) { // IDENTIFY (sharded)
            sendGatewayMessage(OpCode.IDENTIFY, IdentifyShard(token, listOf(shardId, shardCount)), IdentifyShard.serializer())
        } else { // IDENTIFY (unsharded)
            sendGatewayMessage(OpCode.IDENTIFY, Identify(token), Identify.serializer())
        }

        heartbeatJob?.cancel()
        heartbeatJob = heartbeatScope.launch {
            while (this.isActive) {
                if (sequenceNumber != null) {
                    sendGatewayMessage(OpCode.HEARTBEAT, JsonPrimitive(sequenceNumber))
                    delay(hello.heartbeatInterval)
                }
            }
        }
    }

    private suspend fun receiveGatewayMessage(gatewayMessage: GatewayMessage) {
        gatewayMessage.dataPayload
            ?: throw DiscordCompatibilityException("Encountered DiscordEvent ${gatewayMessage.event} without event data")

        val discordEvent = DiscordEvent.values().find { it.name == gatewayMessage.event }
            ?: return // Ignore unknown events, since we receive non-bot events because I guess it's hard for discord to not send bots non-bot events

        logger.debug { "Received Dispatch $discordEvent" }

        if (discordEvent == DiscordEvent.READY) {
            sessionId = Json.nonstrict.fromJson(Ready.serializer(), gatewayMessage.dataPayload).sessionId
        }

        eventListenerScope.launch {
            try {
                dispatchEvent(eventListener, discordEvent, gatewayMessage.dataPayload)
            } catch (e: Throwable) {
                logger.warn(e) { "Dispatched event caused exception $e" }
            }
        }
    }

    private suspend fun sendGatewayMessage(opCode: OpCode, data: JsonElement? = null, event: DiscordEvent? = null) {
        logger.debug { "Sending OpCode: $opCode" }
        val eventName = event?.name ?: ""
        val message = GatewayMessage(opCode, data, sequenceNumber, eventName)
        sendWebsocketMessage!!.invoke(Json.stringify(GatewayMessage.serializer(), message))
    }

    private suspend fun <T> sendGatewayMessage(opCode: OpCode, data: T, serializer: SerializationStrategy<T>, event: DiscordEvent? = null) {
        logger.debug { "Sending OpCode: $opCode" }
        val eventName = event?.name ?: ""
        val message = GatewayMessage(opCode, Json.nonstrict.toJson(serializer, data), sequenceNumber, eventName)
        sendWebsocketMessage!!.invoke(Json.stringify(GatewayMessage.serializer(), message))
    }
}
