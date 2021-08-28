package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.api.channel.ChannelClient
import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.global.GlobalClient
import com.jessecorbett.diskord.api.guild.GuildClient
import com.jessecorbett.diskord.api.invite.InviteClient
import com.jessecorbett.diskord.api.webhook.WebhookClient
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.sendMessage
import com.jessecorbett.diskord.util.sendReply

/**
 * Collection of utilities for dispatched events, such as access to clients
 */
public interface BotContext {
    public val client: RestClient

    // Global functions

    /**
     * Create an instance of the global client
     *
     * TODO: Should this be a property accessor instead?
     */
    public fun global(): GlobalClient = GlobalClient(client)

    /**
     * Create an instance of a guild client
     */
    public fun guild(guildId: String): GuildClient = GuildClient(guildId, client)

    /**
     * Create an instance of a channel client
     */
    public fun channel(channelId: String): ChannelClient = ChannelClient(channelId, client)

    /**
     * Create an instance of an invite client
     */
    public fun invite(inviteCode: String): InviteClient = InviteClient(inviteCode, client)

    /**
     * Create an instance of a webhook client
     */
    public fun webhook(webhookId: String): WebhookClient = WebhookClient(webhookId, client)

    // Message receivers

    /**
     * Create an instance of a channel client for the [Message.channelId]
     */
    public val Message.channel: ChannelClient
        get() = channel(channelId)

    /**
     * Create an instance of a guild client for the [Message.channelId] or null if there is no guild
     */
    public val Message.guild: GuildClient?
        get() = guildId?.let { guild(it) }

    /**
     * Deletes the message
     */
    public suspend fun Message.delete() {
        channel.deleteMessage(id)
    }

    /**
     * Send a message in the same channel as this message.
     *
     * @param message the message to respond with
     *
     * @see reply
     */
    public suspend fun Message.respond(message: String): Message {
        return channel.sendMessage(message)
    }

    /**
     * Send an embed in the same channel as this message.
     *
     * @param message the message to respond with
     * @param block a block to set the embed parameters with
     *
     * @see reply
     */
    public suspend fun Message.respond(message: String = "", block: Embed.() -> Unit): Message {
        return channel.sendMessage(message, Embed().apply { block() })
    }

    /**
     * Send an embed in the same channel as this message.
     *
     * @param message the message to respond with
     * @param block a block to set the embed parameters with
     *
     * @see reply
     */
    @Deprecated(message = "Simplified to respond with optional embed builder", replaceWith = ReplaceWith("respond"))
    public suspend fun Message.respondEmbed(message: String = "", block: Embed.() -> Unit): Message {
        return channel.sendMessage(message, Embed().apply { block() })
    }

    /**
     * Send a message in the same channel as this message and delete the original message.
     *
     * @param message the message to respond with
     *
     * @see reply
     */
    public suspend fun Message.respondAndDelete(message: String): Message {
        delete()
        return channel.sendMessage(message)
    }

    /**
     * Send a message in the same channel as this message and delete the original message.
     *
     * @param message the message to respond with
     * @param block the embed builder
     *
     * @see reply
     */
    public suspend fun Message.respondAndDelete(message: String = "", block: Embed.() -> Unit): Message {
        delete()
        return channel.sendMessage(message, Embed().apply { block() })
    }

    /**
     * Sends a reply to an existing message using the Discord reply feature.
     *
     * @param message the message to reply with
     *
     * @see respond
     */
    public suspend fun Message.reply(message: String): Message {
        return channel.sendReply(this, message)
    }

    /**
     * Sends a reply to an existing message using the Discord reply feature.
     *
     * @param message the message to reply with
     * @param block a block to set the embed parameters with
     *
     * @see respond
     */
    public suspend fun Message.reply(message: String = "", block: Embed.() -> Unit): Message {
        return channel.sendReply(this, message, Embed().apply { block() })
    }

    /**
     * Sends a reply to an existing message using the Discord reply feature.
     *
     * @param message the message to reply with
     * @param block a block to set the embed parameters with
     *
     * @see respond
     */
    @Deprecated(message = "Simplified to reply with optional embed builder", replaceWith = ReplaceWith("reply"))
    public suspend fun Message.replyEmbed(message: String = "", block: Embed.() -> Unit): Message {
        return channel.sendReply(this, message, Embed().apply { block() })
    }

    /**
     * Add a reaction to this message.
     *
     * @param emoji the emoji to react with
     */
    public suspend fun Message.react(emoji: String) {
        return channel.addMessageReaction(id, emoji)
    }
}
