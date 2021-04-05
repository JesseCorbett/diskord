package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.AutoGateway
import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.gateway.model.GatewayIntents
import com.jessecorbett.diskord.internal.client.RestClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import mu.KLogger
import mu.KotlinLogging


/**
 * Function for setting up event listeners
 */
public typealias EventHandler = suspend EventDispatcher<Unit>.() -> Unit

public class BotBase(override val client: RestClient) : BotContext {
    public val logger: KLogger = KotlinLogging.logger {}

    /**
     * Handlers are functions called on top of an event dispatcher
     *
     * We use this intermediary step so that we can invoke the handlers once to compute which event
     * hooks are called to determine the appropriate intents and then we invoke them again on the Real dispatcher
     */
    internal val handlers: MutableList<EventHandler> = mutableListOf({
        onReady { logger.info { "Bot has started and is ready for events" } }
        onResume { logger.info { "Bot has resumed a previous websocket session" } }
    })
    @DiskordDsl
    public fun events(handler: EventHandler) {
        handlers += handler
    }
}

/**
 * Function to initiate a bot using the diskord-bot DSL
 *
 * @param token Discord bot API token
 * @param builder Function to build the bot
 */
public suspend fun bot(token: String, builder: BotBase.() -> Unit) {
    val client = RestClient.default(token)

    val base = BotBase(client)
    base.builder()

    val intents = GatewayIntentsComputer().apply {
        base.handlers.forEach { it() }
    }.intents.map { GatewayIntents(it.mask) }.reduceOrNull { a, b -> a + b } ?: GatewayIntents.NON_PRIVILEGED

    val eventHandler = EventDispatcher.build(CoroutineScope(Dispatchers.Default)).apply {
        base.handlers.forEach { it() }
    }

    val gateway = AutoGateway(
        token = token,
        intents = intents,
        restClient = client,
        eventDispatcher = eventHandler
    )

    gateway.start()
    gateway.block()
}
