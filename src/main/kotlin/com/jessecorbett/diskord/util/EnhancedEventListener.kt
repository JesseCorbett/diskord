package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.model.Channel
import com.jessecorbett.diskord.api.model.Guild
import com.jessecorbett.diskord.api.model.Message
import com.jessecorbett.diskord.api.websocket.EventListener
import com.jessecorbett.diskord.api.websocket.events.MessageUpdate
import kotlinx.coroutines.runBlocking

abstract class EnhancedEventListener(token: String) : EventListener() {
    val clientStore = ClientStore(token)

    suspend fun Message.reply(text: String) = clientStore.channels[this.channelId].sendMessage(text)
    suspend fun Message.delete() = clientStore.channels[this.channelId].deleteMessage(this.id)

    suspend fun MessageUpdate.reply(text: String) = clientStore.channels[this.channelId].sendMessage(text)
    suspend fun MessageUpdate.delete() = clientStore.channels[this.channelId].deleteMessage(this.id)

    val Message.channel: Channel
        get() = runBlocking { clientStore.channels[channelId].get() }

    val Message.guild: Guild?
        get() = channel.guildId?.let { runBlocking { clientStore.guilds[it].get() } }
}
