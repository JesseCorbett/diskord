package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.models.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.api.rest.BulkMessageDelete
import com.jessecorbett.diskord.internal.RateLimitInfo
import com.jessecorbett.diskord.internal.RestClient
import com.jessecorbett.diskord.internal.bodyAs
import com.jessecorbett.diskord.internal.bodyAsListOf
import java.time.Instant

class ChannelClient(token: String, val channelId: String) : RestClient(token) {
    val messageDeleteRateInfo = RateLimitInfo(1, 1, Instant.MAX)

    fun getChannel() = getRequest("/channels/$channelId").bodyAs(Channel::class)

    fun update(channel: Channel) = putRequest("/channels/$channelId", channel).bodyAs(Channel::class)

    fun delete() = deleteRequest("/channels/$channelId").close()

    fun getMessage() = getRequest("/channels/$channelId/messages").bodyAsListOf(Message::class)

    fun getMessage(messageId: String) = getRequest("/channels/$channelId/messages/$messageId").bodyAs(Message::class)

    fun createMessage(message: CreateMessage) = postRequest("/channels/$channelId/messages", message).bodyAs(Message::class)

    fun addMessageReaction(messageId: String, emoji: String) = putRequest("/channels/$channelId/messages/$messageId/reactions/$emoji/@me").close()

    fun removeMessageReaction(messageId: String, emoji: String, userId: String = "@me") = deleteRequest("/channels/$channelId/messages/$messageId/reactions/$emoji/$userId").close()

    fun getMessageReactions(messageId: String, emoji: String): List<Reaction> = getRequest("/channels/$channelId/messages/$messageId/reaction/$emoji").bodyAsListOf(Reaction::class)

    fun deleteAllMessageReactions(messageId: String) = deleteRequest("/channels/$channelId/messages/$messageId/reactions").close()

    fun editMessage(messageId: String, messageEdit: MessageEdit) = putRequest("/channels/$channelId/messages/$messageId", messageEdit).bodyAs(Message::class)

    fun deleteMessage(messageId: String) = deleteRequest("/channels/$channelId/messages/$messageId", messageDeleteRateInfo).close()

    fun bulkDeleteMessages(channelId: String, bulkMessageDelete: BulkMessageDelete) = postRequest("/channels/$channelId/messages/bulk-delete", bulkMessageDelete).close()

    fun editPermissions(overwrite: Overwrite) = putRequest("/channels/$channelId/permissions/${overwrite.id}", overwrite).close()

    fun getInvites() = getRequest("/channels/$channelId/invites").bodyAsListOf(Invite::class)

    fun createInvite(createInvite: CreateInvite) = postRequest("/channels/$channelId/invites", createInvite).bodyAs(Invite::class)

    fun deletePermissions(overwriteId: String) = deleteRequest("/channels/$channelId/permissions/$overwriteId").close()

    fun triggerTypingIndicator() = postRequest("/channels/$channelId/typing").close()

    fun getPinnedMessages() = getRequest("/channels/$channelId/pins").bodyAsListOf(Message::class)

    fun pinMessage(messageId: String) = putRequest("/channels/$channelId/pins/$messageId").close()

    fun unpinMessage(messageId: String) = putRequest("/channels/$channelId/pins/$messageId").close()

    fun addGroupDMRecipient(userId: String, groupDMAddRecipient: GroupDMAddRecipient) = putRequest("/channels/$channelId/recipients/$userId", groupDMAddRecipient).close()

    fun removeGroupDMRecipient(userId: String) = deleteRequest("/channels/$channelId/recipients/$userId").close()

    fun getWebhooks() = getRequest("/channels/$channelId/webhooks").bodyAsListOf(Webhook::class)

    fun createWebhook(webhook: CreateWebhook) = postRequest("/channels/$channelId/webhooks", webhook).bodyAs(Webhook::class)

}
