package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.gateway.events.DiscordEvent

/**
 * Installs the command DSL and provides a scope for defining commands
 */
@DiskordDsl
public fun BotBase.commands(commandPrefix: String = ".", commands: suspend CommandBuilder.() -> Unit) {
    events {
        CommandBuilder(commandPrefix, this).commands()
    }
}

@DiskordDsl
public class CommandBuilder(private val prefix: String, private val dispatcher: EventDispatcher<Unit>) {
    /**
     * Creates a command listener on [DiscordEvent.MESSAGE_CREATE] events
     */
    @DiskordDsl
    public suspend fun command(key: String, block: suspend Message.() -> Unit) {
        dispatcher.onMessageCreate { message ->
            if (message.content.startsWith("$prefix$key ")) {
                message.block()
            }
        }
    }
}
