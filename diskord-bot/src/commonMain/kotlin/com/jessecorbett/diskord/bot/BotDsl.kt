package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.AutoGateway
import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.gateway.EventHandler
import com.jessecorbett.diskord.api.gateway.model.GatewayIntents
import com.jessecorbett.diskord.internal.client.RestClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import mu.KLogger
import mu.KotlinLogging

public typealias EventFilter = suspend EventDispatcher<Boolean>.() -> Unit

@DiskordDsl
public fun buildFilter(builder: EventFilter): EventFilter {
    return builder
}

@DiskordDsl
public suspend fun EventDispatcher<Unit>.filter(filter: EventFilter, handler: EventHandler) {
    val filterDispatcher = this.forType<Boolean>()
    filterDispatcher.filter()
    val results = filterDispatcher.await()
    if (results.all { it }) {
        this.handler()
    }
}

public class BotBase(override val client: RestClient) : BotContext {
    public val logger: KLogger = KotlinLogging.logger {}

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

    // Take all invocations of BotBase.events and turn them into one function for AutoGateway
    val globalEventHandler: EventHandler = {
        base.handlers.forEach { it() }
    }

    val gateway = AutoGateway(
        token = token,
        intents = GatewayIntents.NON_PRIVILEGED,
        eventScope = CoroutineScope(Dispatchers.Default),
        restClient = client,
        eventHandler = globalEventHandler
    )

    gateway.start()
    gateway.block()
}
