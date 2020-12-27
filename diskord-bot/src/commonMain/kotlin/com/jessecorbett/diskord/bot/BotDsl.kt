package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.AutoGateway
import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.gateway.EventFilter
import com.jessecorbett.diskord.api.gateway.EventHandler
import com.jessecorbett.diskord.api.gateway.model.GatewayIntents
import com.jessecorbett.diskord.internal.client.DefaultRestClient
import com.jessecorbett.diskord.util.DiskordInternals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Base class for building a bot
 */
@DiskordDsl
public class BotBase internal constructor() {
    internal var filterFunction: List<EventFilter> = listOf()
    internal var eventsFunction: List<EventHandler> = listOf()

    @DiskordDsl
    public fun filter(filter: EventFilter) {
        filterFunction = filterFunction + filter
    }

    @DiskordDsl
    public fun events(events: EventHandler) {
        eventsFunction = eventsFunction + events
    }
}

/**
 * Function to initiate a bot using the diskord-bot DSL
 *
 * @param token Discord bot API token
 * @param builder Function to build the bot
 */
@OptIn(DiskordInternals::class)
public suspend fun bot(token: String, builder: BotBase.() -> Unit) {
    val client = DefaultRestClient(DiscordUserType.BOT, token)

    val base = BotBase().apply { builder() }

    // Translate the list of EventFilters into one big EventFilter
    val filters: EventFilter = {
        for (filter in base.filterFunction) {
            filter()
        }
    }

    // Translate the list of EventHandlers into one big EventHandler
    val eventHandler: EventHandler = {
        for (handler in base.eventsFunction) {
            handler()
        }
    }

    val gateway = AutoGateway(
        token = token,
        intents = GatewayIntents.NON_PRIVILEGED,
        eventScope = CoroutineScope(Dispatchers.Default),
        restClient = client,
        eventHandler = eventHandler,
        filters = filters
    )

    gateway.start()
    gateway.block()
}
