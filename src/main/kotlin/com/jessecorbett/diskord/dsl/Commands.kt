package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.model.Message
import com.jessecorbett.diskord.util.words

@DiskordDsl
fun Bot.commands(prefix: Char = '.', commands: MutableList<Command> = ArrayList(), block: CommandSet.() -> Unit) {
    CommandSet(commands).apply(block)

    messageCreated { message ->
        if (!message.content.startsWith(prefix)) return@messageCreated
        commands.filter { it.command == message.words[0].drop(1) }.forEach {
            it.action(message)
        }
    }
}

@DiskordDsl
class CommandSet(val commands: MutableList<Command>)

@DiskordDsl
fun CommandSet.command(command: String, action: suspend (Message) -> Unit) {
    commands += Command(command, action)
}

@DiskordDsl
class Command(val command: String, val action: suspend (Message) -> Unit)

fun main() {
    bot("MzQ2NDQ0NjE1ODMxNzgxMzc2.DxWZWQ.cEAHWDZlun4H2Fj2fSt0-mlEcf8") {
        commands {
            command("ping") { message ->
                message.delete()
                println("hewwo")
                message.reply("pong")
            }

            command("shutdown") {
                it.delete()
                shutdown(true)
            }
        }
    }
}
