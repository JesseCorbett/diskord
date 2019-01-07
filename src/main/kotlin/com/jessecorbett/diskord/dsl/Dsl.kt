package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.exception.DiscordException
import com.jessecorbett.diskord.api.model.Message
import com.jessecorbett.diskord.api.websocket.DiscordWebSocket
import com.jessecorbett.diskord.api.websocket.EventListener
import com.jessecorbett.diskord.util.ClientStore
import com.jessecorbett.diskord.util.sendMessage
import com.jessecorbett.diskord.util.words

@DslMarker
annotation class DiskordDsl

@DiskordDsl
class Bot constructor(val token: String) : EventListener() {
    private val clientStore = ClientStore(token)
    private val websocket = DiscordWebSocket(token, this)
    val commands = ArrayList<CommandSet>()
    val responses = ArrayList<String>()

    override suspend fun onMessageCreate(message: Message) {
        commands.filter { message.content.startsWith(it.prefix) }
                .flatMap { it.commands }
                .filter { it.command == message.words[0].drop(1) }
                .forEach { it.block(message, clientStore) }
    }

    fun commands(prefix: Char = '.', block: CommandSet.() -> Unit) {
        val commandSet = CommandSet(clientStore, prefix)
        commandSet.apply(block)
        commands += commandSet
    }
}

fun bot(token: String, block: Bot.() -> Unit) = Bot(token).apply(block)


class CommandSet(private val clientStore: ClientStore, val prefix: Char, val commands: MutableList<Command> = ArrayList()) {
    fun command(command: String, action: suspend (Message, ClientStore) -> Unit) {
        commands + Command(clientStore, command, action)
    }
}

class CommandBuilder(private val clientStore: ClientStore, val command: String, val block: suspend (Message, ClientStore) -> Unit) {
    suspend fun Message.reply(text: String) = clientStore.channels[this.channelId].sendMessage(text)

    suspend fun Message.delete() = clientStore.channels[this.channelId].deleteMessage(this.id)

    fun build() {

    }
}

data class Command(val command, val block: suspend (Message, ClientStore) -> Unit)

fun test() {


    bot("fake-token") {
        commands {
            command("ping") { message, clients ->
                message.reply("pong")
            }
        }
    }


}
