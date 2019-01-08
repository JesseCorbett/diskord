package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.model.Message
import com.jessecorbett.diskord.api.websocket.DiscordWebSocket
import com.jessecorbett.diskord.api.websocket.EventListener
import com.jessecorbett.diskord.util.words
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors


@DiskordDsl
fun Bot.commands(prefix: Char = '.', commands: MutableList<Command> = ArrayList(), block: CommandSet.() -> Unit) {
    CommandSet(commands).apply(block)

    messageCreated { message ->
        if (!message.content.startsWith(prefix)) return@messageCreated
        commands.filter { it.command == message.words[0].drop(1) }.forEach {
            it.block(it, message)
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
class Command(val command: String, val block: suspend Command.(Message) -> Unit)

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
