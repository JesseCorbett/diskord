package com.jessecorbett.diskord.util.experimental

import com.jessecorbett.diskord.api.client.ChannelClient
import com.jessecorbett.diskord.DiscordWebSocket
import com.jessecorbett.diskord.EventListener
import com.jessecorbett.diskord.api.model.Message
import com.jessecorbett.diskord.util.ClientStore
import com.jessecorbett.diskord.util.authorId

class Bot constructor(val token: String) : EventListener() {
    private val clientStore = ClientStore(token)
    private val websocket = DiscordWebSocket(token, this)
    val commands = ArrayList<CommandSet>()
    val responses = ArrayList<String>()

    override suspend fun onMessageCreate(message: Message) {
        for (commandSet in commands) {
            if (message.content.startsWith(commandSet.prefix)) {
                val commandText = message.content.split(" ")[0].drop(1)
                for (command in commandSet.commands) {
                    if (command.key == commandText) {
                        command.value(message.content.split(" ").drop(1), message.authorId, clientStore.channels[message.channelId], clientStore)
                        return
                    }
                }
            }
        }
    }
}

typealias Command = suspend (List<String>, String, ChannelClient, ClientStore) -> Unit

data class CommandSet(val prefix: Char, val commands: MutableMap<String, Command> = HashMap())

fun bot(token: String, block: Bot.() -> Unit) = Bot(token).apply(block)

fun Bot.commands(prefix: Char = '!', block: CommandSet.() -> Unit) {
    val commandSet = CommandSet(prefix)
    commandSet.apply(block)
    commands += commandSet
}

fun CommandSet.command(command: String, action: Command) {
    commands[command] = action
}
