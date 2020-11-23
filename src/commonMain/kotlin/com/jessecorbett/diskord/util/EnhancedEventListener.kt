package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.exceptions.DiscordNotFoundException
import com.jessecorbett.diskord.api.common.Channel
import com.jessecorbett.diskord.api.common.Emoji
import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.api.channel.ChannelClient
import com.jessecorbett.diskord.api.gateway.EventListener
import com.jessecorbett.diskord.api.gateway.events.MessageUpdate
import com.jessecorbett.diskord.dsl.CombinedMessageEmbed
import com.jessecorbett.diskord.dsl.embed


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
     * Convenience function for sending a text message in response to another.
     *
     * @param text The message to send.
     *
     * @return the [Message] created
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    suspend infix fun Message.reply(text: String) =
        clientStore.channels[channelId].sendMessage(text, null)

    /**
     * Convenience function for sending a text message in response to another and deleting the original.
     *
     * @param text The message to send.
     *
     * @return the [Message] created
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    suspend infix fun Message.replyAndDelete(text: String): Message {
        this.delete()
        return clientStore.channels[channelId].sendMessage(text, null)
    }

    /**
     * Convenience function for sending a message in response to another.
     *
     * @param text The message to send.
     * @param embed The embed to include with the message.
     *
     * @return the [Message] created
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    suspend fun Message.reply(text: String, embed: Embed? = null) =
        clientStore.channels[channelId].sendMessage(text, embed)

    /**
     * Convenience function for sending a message in response to another.
     *
     * @param text The message to send.
     * @param embedDsl A usage of the message embed DSL to create the embed object.
     * @see embed
     *
     * @return the [Message] created
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    suspend fun Message.reply(text: String, embedDsl: Embed.() -> Unit) =
        clientStore.channels[channelId].sendMessage(text, embedDsl)

    /**
     * Calls [ChannelClient.createMessage] for sending messages from the [com.jessecorbett.diskord.dsl.CombinedMessageEmbed].
     *
     * @param block The DSL call to build a combination text and embed content.
     * @see com.jessecorbett.diskord.dsl.message
     *
     * @return the created [Message].
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    suspend fun Message.reply(block: CombinedMessageEmbed.() -> Unit) =
        clientStore.channels[channelId].sendMessage(block)

    /**
     * Convenience function for deleting a message.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    suspend fun Message.delete() = try {
        clientStore.channels[channelId].deleteMessage(id)
    } catch (exception: DiscordNotFoundException) {
        // This exception is fine, as it means the message was already deleted by someone else, and we don't care that we didn't get there first
    }

    /**
     * Convenience function to react to a message.
     *
     * @param emoji The text form of an emoji, such as a unicode emoji.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors
     */
    suspend fun Message.react(emoji: String) = clientStore.channels[channelId].addMessageReaction(id, emoji)

    /**
     * Convenience function to react to a message.
     *
     * @param emoji The custom emoji.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors
     */
    suspend fun Message.react(emoji: Emoji) = clientStore.channels[channelId].addMessageReaction(id, emoji)

    /**
     * Convenience property for sending a message in response to another.
     *
     * @param text The message to send.
     * @param embed The optional embed to include with the message.
     *
     * @return the [Message] created
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    suspend fun MessageUpdate.reply(text: String, embed: Embed? = null) =
        clientStore.channels[channelId].sendMessage(text, embed)

    /**
     * Convenience function for sending a message in response to another.
     *
     * @param text The message to send.
     * @param embedDsl A usage of the message embed DSL to create the embed object.
     * @see embed
     *
     * @return the [Message] created
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    suspend fun MessageUpdate.reply(text: String, embedDsl: Embed.() -> Unit) =
        clientStore.channels[channelId].sendMessage(text, embedDsl)

    /**
     * Calls [ChannelClient.createMessage] for sending messages from the [com.jessecorbett.diskord.dsl.CombinedMessageEmbed].
     *
     * @param block The DSL call to build a combination text and embed content.
     * @see com.jessecorbett.diskord.dsl.message
     *
     * @return the created [Message].
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    suspend fun MessageUpdate.reply(block: CombinedMessageEmbed.() -> Unit) =
        clientStore.channels[channelId].sendMessage(block)

    /**
     * Convenience property for deleting a message that was updated.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    suspend fun MessageUpdate.delete() = try {
        clientStore.channels[channelId].deleteMessage(id)
    } catch (exception: DiscordNotFoundException) {
        // This exception is fine, as it means the message was already deleted by someone else, and we don't care that we didn't get there first
    }


    /**
     * Convenience property for getting the full [Channel] from a [Message] instance.
     *
     * @returns the channel object for the channel the message was sent in.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
     */
    val Message.channel: ChannelClient
        get() = clientStore.channels[channelId]
}
