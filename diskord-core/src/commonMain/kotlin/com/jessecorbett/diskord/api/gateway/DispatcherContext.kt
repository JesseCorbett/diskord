package com.jessecorbett.diskord.api.gateway

import com.jessecorbett.diskord.api.channel.ChannelClient
import com.jessecorbett.diskord.api.common.Channel
import com.jessecorbett.diskord.api.common.Guild
import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.guild.GuildClient
import com.jessecorbett.diskord.api.invite.InviteClient
import com.jessecorbett.diskord.api.webhook.WebhookClient
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.sendMessage

/**
 * Collection of utilities for dispatched events, such as access to clients
 */
public interface DispatcherContext {
    public val client: RestClient

    // Global functions

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
     * Get the [Channel] related to the given message
     */
    public suspend fun Message.channel(): Channel {
        return channel.getChannel()
    }

    /**
     * Create an instance of a guild client for the [Message.channelId] or null if there is no guild
     */
    public val Message.guild: GuildClient?
        get() = guildId?.let { guild(it) }

    /**
     * Get the [Guild] related to the given message or null if there is no guild
     */
    public suspend fun Message.guild(withCounts: Boolean = false): Guild? {
        return guild?.getGuild(withCounts)
    }

    /**
     * Deletes the message
     */
    public suspend fun Message.delete() {
        channel.deleteMessage(id)
    }

    /**
     * Sends a message in the same channel without using the discord reply feature
     */
    public suspend fun Message.softReply(message: String): Message {
        return channel.sendMessage(message)
    }
}
