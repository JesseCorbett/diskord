package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.model.Channel
import com.jessecorbett.diskord.api.model.Guild
import com.jessecorbett.diskord.api.model.Message
import com.jessecorbett.diskord.api.websocket.EventListener
import com.jessecorbett.diskord.api.websocket.events.MessageUpdate
import kotlinx.coroutines.runBlocking


/**
 * An [EventListener] inheritor adding a [ClientStore] and related convenience methods.
 *
 * @param token a user token used for the clientStore.
 * @constructor creates a bare bones [EventListener] but with convenience access methods and a clientStore.
 */
abstract class EnhancedEventListener(token: String) : EventListener() {

    /**
     * A common [ClientStore] for use by a class implementing this one.
     *
     * Also used by convenience methods and extensions in this class.
     */
    val clientStore = ClientStore(token)


    /**
     * Convenience property for sending a message in response to another.
     *
     * @return the [Message] created
     * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
     */
    suspend fun Message.reply(text: String) = clientStore.channels[channelId].sendMessage(text)

    /**
     * Convenience property for deleting a message.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
     */
    suspend fun Message.delete() = clientStore.channels[channelId].deleteMessage(id)


    /**
     * Convenience property for sending a message in response to another being edited.
     *
     * @return the [Message] created
     * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
     */
    suspend fun MessageUpdate.reply(text: String) = clientStore.channels[channelId].sendMessage(text)

    /**
     * Convenience property for deleting a message that was updated.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
     */
    suspend fun MessageUpdate.delete() = clientStore.channels[channelId].deleteMessage(id)


    /**
     * Convenience property for getting the full [Channel] from a [Message] instance.
     *
     * @returns the channel object for the channel the message was sent in.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
     */
    val Message.channel: Channel
        get() = runBlocking { clientStore.channels[channelId].get() }

    /**
     * Convenience property for getting the full [Guild] from a [Message] instance.
     *
     * @returns the guild object for the guild the message was sent in. Null if the message was a DM.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
     */
    val Message.guild: Guild?
        get() = channel.guildId?.let { runBlocking { clientStore.guilds[it].get() } }
}
