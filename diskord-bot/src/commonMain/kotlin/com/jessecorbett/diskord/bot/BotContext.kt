package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.api.channel.ChannelClient
import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.global.GlobalClient
import com.jessecorbett.diskord.api.guild.GuildClient
import com.jessecorbett.diskord.api.invite.InviteClient
import com.jessecorbett.diskord.api.webhook.WebhookClient
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.sendMessage

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
     * Sends a message in the same channel without using the discord reply feature
     */
    public suspend fun Message.respond(message: String): Message {
        return channel.sendMessage(message)
    }
}
