package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.gateway.EventDispatcher

/**
 * Allows simply forwarding events on to user space
 */
@DiskordDsl
public fun BotBase.events(eventsBuilder: EventDispatcherWithContext.() -> Unit) {
    registerModule { dispatcher, context ->
        EventDispatcherWithContext(dispatcher, context).eventsBuilder()
    }
}

/**
 * I'm at the (pizza hut) dispatcher,
 * I'm at the (taco bell) bot context,
 * I'm at the combination dispatcher and bot context
 */
@DiskordDsl
public class EventDispatcherWithContext(
    private val dispatcher: EventDispatcher<Unit>,
    private val context: BotContext
) : EventDispatcher<Unit> by dispatcher, BotContext by context
