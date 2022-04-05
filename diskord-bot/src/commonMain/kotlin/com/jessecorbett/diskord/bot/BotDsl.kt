package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.AutoGateway
import com.jessecorbett.diskord.api.common.UserStatus
import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.gateway.model.ActivityType
import com.jessecorbett.diskord.api.gateway.model.GatewayIntents
import com.jessecorbett.diskord.api.gateway.model.UserStatusActivity
import com.jessecorbett.diskord.internal.client.RestClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import mu.KLogger
import mu.KotlinLogging

public class BotBase {
    public val logger: KLogger = KotlinLogging.logger {}

    /**
     * Modules are plugins which implement bot functionality
     */
    public var modules: List<BotModule> = emptyList()
        private set

    public lateinit var gateway: AutoGateway

    init {
        // Simple module for logging bot state and handling interactions pings
        registerModule { dispatcher, _ ->
            dispatcher.onReady { logger.info { "Bot has started and is ready for events" } }
            dispatcher.onResume { logger.info { "Bot has resumed a previous websocket session" } }
        }
    }

    public fun registerModule(botModule: BotModule) {
        modules = modules + botModule
    }

    public fun interface BotModule {
        public suspend fun register(dispatcher: EventDispatcher<Unit>, context: BotContext)
    }

    /**
     * Shutdown this bot instance.
     */
    public suspend fun shutdown() {
        gateway.stop()
    }

    /**
     * Sets the status of the bot
     *
     * @param status The user status text
     * @param userStatus The user state
     */
    public suspend fun setStatus(status: String, userStatus: UserStatus = UserStatus.ONLINE) {
        gateway.setStatus(
            userStatus,
            false,
            null,
            UserStatusActivity(
                status,
                ActivityType.GAME
            )
        )
    }

    /**
     * Sets the status of the bot
     *
     * @param status The user status activity
     * @param afk Whether the user is AFK
     * @param idleTime How long the user has been idle
     * @param userStatus The user state
     */
    public suspend fun setStatus(
        status: UserStatusActivity,
        afk: Boolean = false,
        idleTime: Int? = null,
        userStatus: UserStatus = UserStatus.ONLINE
    ) {
        gateway.setStatus(
            userStatus,
            afk,
            idleTime,
            status
        )
    }
}

/**
 * Function to initiate a bot using the diskord-bot DSL
 *
 * @param token Discord bot API token
 * @param builder Function to build the bot
 */
public suspend fun bot(token: String, builder: suspend BotBase.() -> Unit) {
    val client = RestClient.default(token)

    // Contains the rest client and provides the context for related bot utils
    val virtualContext: BotContext = object : BotContext {
        override val client: RestClient
            get() = client
    }

    // Container for modules and utils like the logger
    val base = BotBase().apply { builder() }

    // Compute intents from the provided modules
    val intentsComputer = GatewayIntentsComputer()
    base.modules.forEach { it.register(intentsComputer, virtualContext) }
    val intents = intentsComputer.intents
        .map { GatewayIntents(it.mask) }
        .reduceOrNull { a, b -> a + b }
        ?: GatewayIntents.NON_PRIVILEGED

    // Create the real dispatcher and register the modules with it
    val dispatcher = EventDispatcher.build(CoroutineScope(Dispatchers.Default))
    base.modules.forEach { it.register(dispatcher, virtualContext) }

    // Create the autogateway using what we've constructed
    val gateway = AutoGateway(
        token = token,
        intents = intents,
        restClient = client,
        eventDispatcher = dispatcher
    )
    base.gateway = gateway

    // Start the gateway, block, and gracefully close after
    gateway.start()
    gateway.block()
    if (gateway.isRunning) {
        gateway.stop()
    }
}
