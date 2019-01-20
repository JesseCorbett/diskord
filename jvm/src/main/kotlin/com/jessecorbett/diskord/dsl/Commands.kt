package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.model.Message
import com.jessecorbett.diskord.util.isFromUser

/**
 * DSL function for hooking a [CommandSet] into a [Bot] DSL instance.
 *
 * @receiver A Bot DSL instance.
 * @param prefix The prefix that all commands for this command set should start with. Defaults to '.'
 * @param commands The list to populate with commands. Defaults to a new empty list.
 * @param block The DSL lambda in which to run [CommandSet.command].
 */
@DiskordDsl
fun Bot.commands(prefix: String = ".", commands: MutableList<Command> = ArrayList(), block: CommandSet.() -> Unit) {
    val set = CommandSet(prefix, commands).apply(block)

    messageCreated { message ->
        commands.filter { message.content.startsWith(set.prefix + it.command) }.forEach {
            if (it.allowBots || message.isFromUser) it.action(message)
        }
    }
}

/**
 * Command DSL set of commands.
 *
 * Simple container of [Command] instances wrapped in [DiskordDsl] to indicate to compiler that this is a DSL class.
 *
 * @param prefix The prefix the bot is looking for to determine if a message is a command.
 * @param commands The list of commands this class wraps.
 */
@DiskordDsl
class CommandSet(var prefix: String = ".", val commands: MutableList<Command>)

/**
 * DSL function for adding a [Command] to a [CommandSet].
 *
 * @receiver The command set to add the new command to.
 * @param command The text for the command, not including prefix.
 * @param allowBots Should bots be allowed to invoke this command. Defaults to false.
 * @param action The lambda to run when a [Message] is created that matches the command and prefix.
 */
@DiskordDsl
fun CommandSet.command(command: String, allowBots: Boolean = false, action: suspend Message.() -> Unit) {
    commands += Command(command, allowBots, action)
}

/**
 * Command DSL object.
 *
 * Encapsulates a single command and action lambda pair.
 *
 * @param command The text for the command, not including prefix.
 * @param allowBots Should bots be allowed to invoke this command. Defaults to false.
 * @param action The lambda to run when a [Message] is created that matches the command and prefix.
 */
@DiskordDsl
class Command(val command: String, val allowBots: Boolean = false, val action: suspend Message.() -> Unit)
