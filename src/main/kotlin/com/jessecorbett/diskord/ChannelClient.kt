package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.internal.*
import java.time.Instant

class ChannelClient(token: DiscordToken, val channelId: String) : RestClient(token) {
    val messageDeleteRateInfo = RateLimitInfo(1, 1, Instant.MAX)

    suspend fun getChannel() = getRequest("/channels/$channelId").bodyAs<Channel>()

    suspend fun update(channel: Channel) = putRequest("/channels/$channelId", channel).bodyAs<Channel>()

    suspend fun delete() = deleteRequest("/channels/$channelId").close()

    suspend fun getMessages() = getRequest("/channels/$channelId/messages").bodyAsList<Message>()

    suspend fun getMessage(messageId: String) = getRequest("/channels/$channelId/messages/$messageId").bodyAs<Message>()

    suspend fun createMessage(message: CreateMessage) = postRequest("/channels/$channelId/messages", message).bodyAs<Message>()

    suspend fun addMessageReaction(messageId: String, emoji: String) = putRequest("/channels/$channelId/messages/$messageId/reactions/$emoji/@me").close()

    suspend fun removeMessageReaction(messageId: String, emoji: String, userId: String = "@me") = deleteRequest("/channels/$channelId/messages/$messageId/reactions/$emoji/$userId").close()

    suspend fun getMessageReactions(messageId: String, emoji: String) = getRequest("/channels/$channelId/messages/$messageId/reaction/$emoji").bodyAsList<Reaction>()

    suspend fun deleteAllMessageReactions(messageId: String) = deleteRequest("/channels/$channelId/messages/$messageId/reactions").close()

    suspend fun editMessage(messageId: String, messageEdit: MessageEdit) = putRequest("/channels/$channelId/messages/$messageId", messageEdit).bodyAs<Message>()

    suspend fun deleteMessage(messageId: String) = deleteRequest("/channels/$channelId/messages/$messageId", messageDeleteRateInfo).close()

    suspend fun bulkDeleteMessages(channelId: String, bulkMessageDelete: BulkMessageDelete) = postRequest("/channels/$channelId/messages/bulk-delete", bulkMessageDelete).close()

    suspend fun editPermissions(overwrite: Overwrite) = putRequest("/channels/$channelId/permissions/${overwrite.id}", overwrite).close()

    suspend fun getInvites() = getRequest("/channels/$channelId/invites").bodyAsList<Invite>()

    suspend fun createInvite(createInvite: CreateInvite) = postRequest("/channels/$channelId/invites", createInvite).bodyAs<Invite>()

    suspend fun deletePermissions(overwriteId: String) = deleteRequest("/channels/$channelId/permissions/$overwriteId").close()

    suspend fun triggerTypingIndicator() = postRequest("/channels/$channelId/typing").close()

    suspend fun getPinnedMessages() = getRequest("/channels/$channelId/pins").bodyAsList<Message>()

    suspend fun pinMessage(messageId: String) = putRequest("/channels/$channelId/pins/$messageId").close()

    suspend fun unpinMessage(messageId: String) = putRequest("/channels/$channelId/pins/$messageId").close()

    suspend fun addGroupDMRecipient(userId: String, groupDMAddRecipient: GroupDMAddRecipient) = putRequest("/channels/$channelId/recipients/$userId", groupDMAddRecipient).close()

    suspend fun removeGroupDMRecipient(userId: String) = deleteRequest("/channels/$channelId/recipients/$userId").close()

    suspend fun getWebhooks() = getRequest("/channels/$channelId/webhooks").bodyAsList<Webhook>()

    suspend fun createWebhook(webhook: CreateWebhook) = postRequest("/channels/$channelId/webhooks", webhook).bodyAs<Webhook>()
}
