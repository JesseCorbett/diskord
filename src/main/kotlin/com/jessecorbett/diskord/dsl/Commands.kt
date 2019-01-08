package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.model.Message

@DiskordDsl
fun Bot.commands(prefix: String = ".", commands: MutableList<Command> = ArrayList(), block: CommandSet.() -> Unit) {
    CommandSet(commands).apply(block)

    messageCreated { message ->
        commands.filter { message.content.startsWith(prefix + it.command) }.forEach {
            it.action(it, message)
        }
    }
}

@DiskordDsl
class CommandSet(val commands: MutableList<Command>)

@DiskordDsl
fun CommandSet.command(command: String, action: suspend Command.(Message) -> Unit) {
    commands += Command(command, action)
}

@DiskordDsl
class Command(val command: String, val action: suspend Command.(Message) -> Unit)
