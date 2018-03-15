package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.models.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.api.rest.BulkMessageDelete
import com.jessecorbett.diskord.internal.RateLimitInfo
import java.time.Instant

class ChannelClient(token: String, val channelId: String) : RestClient(token) {
    val messageDeleteRateInfo = RateLimitInfo(1, 1, Instant.MAX)

    fun getChannel() = getRequest("/channels/$channelId").bodyAs(Channel::class)

    fun updateChannel(channel: Channel) = putRequest("/channels/$channelId", channel).bodyAs(Channel::class)

    fun deleteChannel() {
        deleteRequest("/channels/$channelId")
    }

    fun getChannelMessages() = getRequest("/channels/$channelId/messages").bodyAsListOf(Message::class)

    fun getChannelMessage(messageId: String) = getRequest("/channels/$channelId/messages/$messageId").bodyAs(Message::class)

    fun createMessage(message: CreateMessage) = postRequest("/channels/$channelId/messages", message).bodyAs(Message::class)

    fun addMessageReaction(messageId: String, emoji: String) {
        putRequest("/channels/$channelId/messages/$messageId/reactions/$emoji/@me")
    }

    fun removeMessageReaction(messageId: String, emoji: String, userId: String = "@me") {
        deleteRequest("/channels/$channelId/messages/$messageId/reactions/$emoji/$userId")
    }

    fun getMessageReactions(messageId: String, emoji: String): List<Reaction> = getRequest("/channels/$channelId/messages/$messageId/reaction/$emoji").bodyAsListOf(Reaction::class)

    fun deleteAllMessageReactions(messageId: String) {
        deleteRequest("/channels/$channelId/messages/$messageId/reactions")
    }

    fun editMessage(messageId: String, messageEdit: MessageEdit) = putRequest("/channels/$channelId/messages/$messageId", messageEdit).bodyAs(Message::class)

    fun deleteMessage(messageId: String) {
        deleteRequest("/channels/$channelId/messages/$messageId", messageDeleteRateInfo)
    }

    fun bulkDeleteChannelMessages(channelId: String, bulkMessageDelete: BulkMessageDelete) {
        postRequest("/channels/$channelId/messages/bulk-delete", bulkMessageDelete)
    }

    fun editChannelPermissions(overwrite: Overwrite) {
        putRequest("/channels/$channelId/permissions/${overwrite.id}", overwrite)
    }

    fun getChannelInvites() = getRequest("/channels/$channelId/invites").bodyAsListOf(Invite::class)

    fun createChannelInvite(createInvite: CreateInvite) = postRequest("/channels/$channelId/invites", createInvite).bodyAs(Invite::class)

    fun deleteChannelPermissions(overwriteId: String) {
        deleteRequest("/channels/$channelId/permissions/$overwriteId")
    }

    fun triggerTypingIndicator() {
        postRequest("/channels/$channelId/typing")
    }

    fun getPinnedMessages() = getRequest("/channels/$channelId/pins").bodyAsListOf(Message::class)

    fun pinMessage(messageId: String) {
        putRequest("/channels/$channelId/pins/$messageId")
    }

    fun unpinMessage(messageId: String) {
        putRequest("/channels/$channelId/pins/$messageId")
    }

    fun addGroupDMRecipient(userId: String, groupDMAddRecipient: GroupDMAddRecipient) {
        putRequest("/channels/$channelId/recipients/$userId", groupDMAddRecipient)
    }

    fun removeGroupDMRecipient(userId: String) {
        deleteRequest("/channels/$channelId/recipients/$userId")
    }

    fun getChannelWebhooks() = getRequest("/channels/$channelId/webhooks").bodyAsListOf(Webhook::class)

    fun createWebhook(webhook: CreateWebhook) = postRequest("/channels/$channelId/webhooks", webhook).bodyAs(Webhook::class)

}
