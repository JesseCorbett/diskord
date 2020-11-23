package com.jessecorbett.diskord.api.gateway

import com.jessecorbett.diskord.api.common.UserStatus
import com.jessecorbett.diskord.api.exceptions.DiscordCompatibilityException
import com.jessecorbett.diskord.api.gateway.commands.Identify
import com.jessecorbett.diskord.api.gateway.commands.IdentifyShard
import com.jessecorbett.diskord.api.gateway.commands.Resume
import com.jessecorbett.diskord.api.gateway.commands.UpdateStatus
import com.jessecorbett.diskord.api.gateway.events.DiscordEvent
import com.jessecorbett.diskord.api.gateway.events.Hello
import com.jessecorbett.diskord.api.gateway.events.Ready
import com.jessecorbett.diskord.api.gateway.model.GatewayIntents
import com.jessecorbett.diskord.api.gateway.model.GatewayMessage
import com.jessecorbett.diskord.api.gateway.model.OpCode
import com.jessecorbett.diskord.api.gateway.model.UserStatusActivity
import com.jessecorbett.diskord.api.global.GatewayBotUrl
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.defaultJson
import kotlinx.coroutines.*
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import mu.KotlinLogging

@OptIn(DiskordInternals::class)
class GatewaySession(
    private val eventListener: EventListener,
    private val token: String,
    gatewayBotUrl: GatewayBotUrl,
    private val intents: GatewayIntents = GatewayIntents.NON_PRIVILEGED,
    private val eventListenerScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    private val shardCount: Int = 0,
    private val shardNumber: Int = 0
) {
    private val logger = KotlinLogging.logger {}

    private val socketManager = SocketManager(gatewayBotUrl.url, ::receiveGatewayMessage)
    private var sessionId: String? = null
    private var sequenceNumber: Int? = null

    val running: Boolean
        get() = socketManager.running

    val alive: Boolean
        get() = socketManager.alive

    suspend fun startSession(sessionId: String? = null, sequenceNumber: Int? = null) {
        this.sessionId = sessionId
        this.sequenceNumber = sequenceNumber
        socketManager.open()
    }

    suspend fun closeSession() {
        socketManager.close()
    }

    suspend fun restartConnection() {
        socketManager.restartConnection()
    }

    suspend fun send(gatewayMessage: GatewayMessage) {
        socketManager.send(gatewayMessage)
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
        socketManager.send(GatewayMessage(OpCode.STATUS_UPDATE, defaultJson.encodeToJsonElement(UpdateStatus(idleTime, activity, status, isAfk))))
    }

    private suspend fun receiveGatewayMessage(gatewayMessage: GatewayMessage) = coroutineScope {
        logger.trace { "Received OpCode ${gatewayMessage.opCode}" }
        when (gatewayMessage.opCode) {
            OpCode.DISPATCH -> {
                sequenceNumber = gatewayMessage.sequenceNumber
                receiveDispatch(gatewayMessage)
            }
            OpCode.HEARTBEAT -> { send(GatewayMessage(OpCode.HEARTBEAT_ACK, null)) }
            OpCode.RECONNECT -> {
                logger.info { "Server requested a reconnect" }
                restartConnection()
            }
            OpCode.INVALID_SESSION -> {
                logger.warn { "The session was invalid, falling back to new session behavior" }
                sessionId = null
                sequenceNumber = null
                restartConnection()
            }
            OpCode.HELLO -> {
                initializeSession(defaultJson.decodeFromJsonElement(Hello.serializer(), gatewayMessage.dataPayload!!))
            }
            OpCode.HEARTBEAT_ACK -> {
                // TODO: We should handle errors to do with a lack of heartbeat ack, possibly restart.
                // Additional note, I've not observed Discord actually sending heartbeats
            }
            else -> throw DiscordCompatibilityException("Reached unreachable OpCode: ${gatewayMessage.opCode}")
        }
    }


    private var heartbeatJob: Job? = null
    private suspend fun initializeSession(hello: Hello) = coroutineScope {
        if (sessionId != null && sequenceNumber != null) { // RESUME
            send(GatewayMessage(OpCode.RESUME, defaultJson.encodeToJsonElement(Resume(token, sessionId!!, sequenceNumber!!))))
        } else if (shardCount != 0) { // IDENTIFY (sharded)
            val identify = IdentifyShard(token, listOf(shardNumber, shardCount), intents = intents)
            send(GatewayMessage(OpCode.IDENTIFY, defaultJson.encodeToJsonElement(identify)))
        } else { // IDENTIFY (unsharded)
            val identify = Identify(token, intents = intents)
            send(GatewayMessage(OpCode.IDENTIFY, defaultJson.encodeToJsonElement(identify)))
        }

        heartbeatJob?.cancel()
        heartbeatJob = launch {
            while (this.isActive) {
                if (sequenceNumber != null) {
                    send(GatewayMessage(OpCode.HEARTBEAT, JsonPrimitive(sequenceNumber)))
                    delay(hello.heartbeatInterval)
                }
            }
        }
    }

    private suspend fun receiveDispatch(gatewayMessage: GatewayMessage) {
        val discordEvent = DiscordEvent.values().find { it.name == gatewayMessage.event }
            ?: return // Ignore unknown events, since we receive non-bot events because I guess it's hard for discord to not send bots non-bot events
        val json = gatewayMessage.dataPayload
            ?: throw DiscordCompatibilityException("Encountered DiscordEvent ${gatewayMessage.event} without event data")

        logger.debug { "Received Dispatch $discordEvent" }

        if (discordEvent == DiscordEvent.READY) {
            sessionId = defaultJson.decodeFromJsonElement<Ready>(gatewayMessage.dataPayload).sessionId
        }

        eventListenerScope.launch {
            try {
                dispatchEvent(eventListener, discordEvent, gatewayMessage.dataPayload)
            } catch (e: Throwable) {
                logger.warn(e) { "Dispatched event caused exception $e" }
            }
        }
    }
}
