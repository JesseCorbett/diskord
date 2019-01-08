package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.model.Message
import com.jessecorbett.diskord.api.model.MessageDelete
import com.jessecorbett.diskord.api.websocket.DiscordWebSocket
import com.jessecorbett.diskord.api.websocket.EventListener
import com.jessecorbett.diskord.api.websocket.events.MessageUpdate
import com.jessecorbett.diskord.util.ClientStore
import com.jessecorbett.diskord.util.sendMessage

@DslMarker
annotation class DiskordDsl

class Bot(token: String) : EventListener() {
    private val websocket = DiscordWebSocket(token, this)
    val clientStore = ClientStore(token)

    fun shutdown(forceClose: Boolean = false) = websocket.close(forceClose)

    suspend fun Message.reply(text: String) = clientStore.channels[this.channelId].sendMessage(text)
    suspend fun Message.delete() = clientStore.channels[this.channelId].deleteMessage(this.id)

    suspend fun MessageUpdate.reply(text: String) = clientStore.channels[this.channelId].sendMessage(text)
    suspend fun MessageUpdate.delete() = clientStore.channels[this.channelId].deleteMessage(this.id)

    @DiskordDsl
    fun messageCreated(block: suspend (Message) -> Unit) { messageCreateHooks += block }
    private val messageCreateHooks: MutableList<suspend (Message) -> Unit> = ArrayList()
    override suspend fun onMessageCreate(message: Message) { messageCreateHooks.forEach { it(message) } }

    @DiskordDsl
    fun messageUpdated(block: suspend (MessageUpdate) -> Unit) { messageUpdateHooks += block }
    private val messageUpdateHooks: MutableList<suspend (MessageUpdate) -> Unit> = ArrayList()
    override suspend fun onMessageUpdate(message: MessageUpdate) { messageUpdateHooks.forEach { it(message) } }

    @DiskordDsl
    fun messageDeleted(block: suspend (MessageDelete) -> Unit) { messageDeleteHooks += block }
    private val messageDeleteHooks: MutableList<suspend (MessageDelete) -> Unit> = ArrayList()
    override suspend fun onMessageDelete(message: MessageDelete) { messageDeleteHooks.forEach { it(message) } }

}

@DiskordDsl
fun bot(token: String, block: Bot.() -> Unit) = Bot(token).apply(block)
