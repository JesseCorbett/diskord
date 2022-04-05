package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.gateway.events.DiscordEvent

@DslMarker
public annotation class ClassicCommandModule

/**
 * Installs the classic command DSL and provides a scope for defining commands
 */
@ClassicCommandModule
public fun BotBase.classicCommands(commandPrefix: String = ".", commands: CommandBuilder.() -> Unit) {
    registerModule { dispatcher, context ->
        CommandBuilder(commandPrefix, dispatcher, context).commands()
    }
}

@ClassicCommandModule
public class CommandBuilder(
    private val prefix: String,
    private val dispatcher: EventDispatcher<Unit>,
    private val botContext: BotContext
) {
    /**
     * Creates a command listener on [DiscordEvent.MESSAGE_CREATE] events
     */
    @ClassicCommandModule
    public fun command(vararg key: String, block: suspend BotContext.(Message) -> Unit) {
        dispatcher.onMessageCreate { message ->
            if (key.any { keyInMessage(it, message.content) }) {
                botContext.block(message)
            }
        }
    }

    /**
     * Detects whether the message starts with the command prefix and the command key
     */
    private fun keyInMessage(key: String, message: String): Boolean {
        return message.startsWith("$prefix$key ") || message == "$prefix$key"
    }
}
