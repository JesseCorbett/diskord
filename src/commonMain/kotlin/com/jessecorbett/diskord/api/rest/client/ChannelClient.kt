package com.jessecorbett.diskord.api.rest.client

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.api.rest.BulkMessageDelete
import com.jessecorbett.diskord.api.rest.client.internal.DefaultRateLimitedRestClient
import com.jessecorbett.diskord.api.rest.client.internal.RateLimitInfo
import com.jessecorbett.diskord.api.rest.client.internal.RateLimitedRestClient
import com.jessecorbett.diskord.internal.urlEncode
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.list

/**
 * A REST client for a specific channel and it's content.
 *
 * @param token The user's API token.
 * @param channelId The id of the channel.
 * @param userType The user type, assumed to be a bot.
 */
@UseExperimental(DiskordInternals::class)
class ChannelClient(
    token: String,
    val channelId: String,
    userType: DiscordUserType = DiscordUserType.BOT,
    client: RateLimitedRestClient = DefaultRateLimitedRestClient(token, userType)
) : RateLimitedRestClient by client {

    /**
     * Message deletion per channel has it's own rate limit.
     */
    val messageDeleteRateInfo = RateLimitInfo(1, 1, Long.MAX_VALUE)

    /**
     * Get this channel.
     *
     * @return This channel.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun get() = getRequest("/channels/$channelId", Channel.serializer())

    /**
     * Update this channel.
     *
     * @param channel The new channel.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun update(channel: Channel) =
        putRequest("/channels/$channelId", channel, Channel.serializer(), Channel.serializer())

    /**
     * Delete this channel. Use with caution, cannot be undone except for DMs.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun delete() = deleteRequest("/channels/$channelId")

    /**
     * Get messages from this channel.
     *
     * @param limit The max number of messages to return, between 1 and 100. Defaults to 50.
     *
     * @return A list of messages.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getMessages(limit: Int = 50) =
        getRequest("/channels/$channelId/messages?limit=$limit", Message.serializer().list)

    /**
     * Get messages from this channel, around a given message.
     *
     * @param limit The max number of messages to return, between 1 and 100. Defaults to 50.
     * @param messageId The message to get messages around.
     *
     * @return A list of messages around the specified message.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getMessagesAround(limit: Int = 50, messageId: String) =
        getRequest("/channels/$channelId/messages?limit=$limit&around=$messageId", Message.serializer().list)

    /**
     * Get messages from this channel, before a given message.
     *
     * @param limit The max number of messages to return, between 1 and 100. Defaults to 50.
     * @param messageId The message to get messages before.
     *
     * @return A list of messages before the specified message.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getMessagesBefore(limit: Int = 50, messageId: String) =
        getRequest("/channels/$channelId/messages?limit=$limit&before=$messageId", Message.serializer().list)

    /**
     * Get messages from this channel, after a given message.
     *
     * @param limit The max number of messages to return, between 1 and 100. Defaults to 50.
     * @param messageId The message to get messages after.
     *
     * @return A list of messages after the specified message.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getMessagesAfter(limit: Int = 50, messageId: String) =
        getRequest("/channels/$channelId/messages?limit=$limit&after=$messageId", Message.serializer().list)

    /**
     * Get a specific message from this channel.
     *
     * @param messageId The id of the message to get.
     *
     * @return The requested message.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getMessage(messageId: String) =
        getRequest("/channels/$channelId/messages/$messageId", Message.serializer())

    /**
     * Create a message in this channel.
     *
     * @param message The message to create.
     *
     * @return The created message.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createMessage(message: CreateMessage) =
        postRequest("/channels/$channelId/messages", message, CreateMessage.serializer(), Message.serializer())

    /**
     * Create a message in this channel with an attachment.
     *
     * @param message The message to create.
     * @param attachment The attachment to add to the message.
     *
     * @return The created message.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createMessage(
        message: CreateMessage,
        attachment: FileData
    ) = postMultipartRequest("/channels/$channelId/messages", message, CreateMessage.serializer(), Message.serializer()) {
        formData {
            append("file", attachment.packet, Headers.build {
                append(HttpHeaders.ContentDisposition,
                    """form-data; name="file"; filename="${attachment.filename}"""")
            })
        }
    }

    /**
     * Add a reaction to a message.
     *
     * Required for unicode emoji, custom emoji must be formatted.
     *
     * @param messageId The message to react to.
     * @param emojiText The text of the emoji to react with.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun addMessageReaction(messageId: String, emojiText: String) =
        putRequest("/channels/$channelId/messages/$messageId/reactions/${urlEncode(emojiText)}/@me")

    /**
     * Add a reaction to a message.
     *
     * @param messageId The message to react to.
     * @param emoji The custom emoji to react with.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun addMessageReaction(messageId: String, emoji: Emoji) = addMessageReaction(messageId, emoji.stringified)

    /**
     * Remove a reaction from a message.
     *
     * Required for unicode emoji, custom emoji must be formatted.
     *
     * @param messageId The message to remove the reaction from.
     * @param emojiText The text of the emoji to remove.
     * @param userId The user whose reaction to remove. Defaults to the current user's reaction.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun removeMessageReaction(messageId: String, emojiText: String, userId: String = "@me") =
        deleteRequest("/channels/$channelId/messages/$messageId/reactions/${urlEncode(emojiText)}/$userId")

    /**
     * Remove a reaction from a message.
     *
     * @param messageId The message to remove the reaction from.
     * @param emoji The custom emoji to remove.
     * @param userId The user whose reaction to remove. Defaults to the current user's reaction.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun removeMessageReaction(messageId: String, emoji: Emoji, userId: String = "@me") =
        removeMessageReaction(messageId, emoji.stringified, userId)

    /**
     * Get all reactions from a message for a given emoji.
     *
     * Required for unicode emoji, custom emoji must be formatted.
     *
     * @param messageId The message to get reactions from.
     * @param textEmoji The text of the emoji to get reactions for.
     *
     * @return The reactions for the given emoji on the given message.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getMessageReactions(messageId: String, textEmoji: String) =
        getRequest("/channels/$channelId/messages/$messageId/reactions/${urlEncode(textEmoji)}", User.serializer().list)

    /**
     * Get all reactions from a message for a given custom emoji.
     *
     * @param messageId The message to get reactions from.
     * @param emoji The custom emoji to get reactions for.
     *
     * @return The reactions for the given emoji on the given message.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getMessageReactions(messageId: String, emoji: Emoji) = getMessageReactions(messageId, emoji.stringified)

    /**
     * Delete all reactions from a message.
     *
     * @param messageId The message to remove reactions from.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun deleteAllMessageReactions(messageId: String) =
        deleteRequest("/channels/$channelId/messages/$messageId/reactions")

    /**
     * Edit a message in this channel.
     *
     * @param messageId The message to edit.
     * @param messageEdit The edits to make.
     *
     * @return The edited message.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun editMessage(messageId: String, messageEdit: MessageEdit) = patchRequest(
        "/channels/$channelId/messages/$messageId",
        messageEdit,
        MessageEdit.serializer(),
        Message.serializer()
    )

    /**
     * Delete a message in this channel.
     *
     * @param messageId The message to delete.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun deleteMessage(messageId: String) =
        deleteRequest("/channels/$channelId/messages/$messageId", messageDeleteRateInfo)

    /**
     * Bulk delete messages in this channel.
     *
     * @param bulkMessageDelete The messages to delete.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun bulkDeleteMessages(bulkMessageDelete: BulkMessageDelete) =
        postRequest("/channels/$channelId/messages/bulk-delete", bulkMessageDelete, BulkMessageDelete.serializer())

    /**
     * Edit the permissions for this channel.
     *
     * @param overwrite The updated permissions.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun editPermissions(overwrite: Overwrite) =
        putRequest("/channels/$channelId/permissions/${overwrite.id}", overwrite, Overwrite.serializer())

    /**
     * Get the invites for this channel.
     *
     * @return The list of invites for this channel.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getInvites() = getRequest("/channels/$channelId/invites", Invite.serializer().list)

    /**
     * Create an invite for this channel.
     *
     * @param createInvite The invite to create.
     *
     * @return The created invite.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createInvite(createInvite: CreateInvite) =
        postRequest("/channels/$channelId/invites", createInvite, CreateInvite.serializer(), Invite.serializer())

    /**
     * Delete a permissions set for this channel.
     *
     * @param overwriteId The permissions set to delete.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun deletePermissions(overwriteId: String) = deleteRequest("/channels/$channelId/permissions/$overwriteId")

    /**
     * Indicate that the current user is typing in this channel.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun triggerTypingIndicator() = postRequest("/channels/$channelId/typing")

    /**
     * Get the pinned messages from this channel.
     *
     * @return The pinned messages.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getPinnedMessages() = getRequest("/channels/$channelId/pins", Message.serializer().list)

    /**
     * Pin a message in this channel.
     *
     * @param messageId The message to pin.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun pinMessage(messageId: String) = putRequest("/channels/$channelId/pins/$messageId")

    /**
     * Unpin a message in this channel.
     *
     * @param messageId The message to unpin.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun unpinMessage(messageId: String) = putRequest("/channels/$channelId/pins/$messageId")

    /**
     * Add a user to this group DM channel.
     *
     * Requires this channel be a group DM and you have the user's OAuth access token.
     *
     * @param userId The user to add.
     * @param groupDMAddRecipient The user access token and optional nickname.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun addGroupDMRecipient(userId: String, groupDMAddRecipient: GroupDMAddRecipient) =
        putRequest("/channels/$channelId/recipients/$userId", groupDMAddRecipient, GroupDMAddRecipient.serializer())

    /**
     * Remove a user from this group DM.
     *
     * Requires this channel be a group DM.
     *
     * @param userId The user to remove.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun removeGroupDMRecipient(userId: String) = deleteRequest("/channels/$channelId/recipients/$userId")

    /**
     * Get the webhooks in this channel.
     *
     * @return The list webhooks present.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getWebhooks() = getRequest("/channels/$channelId/webhooks", Webhook.serializer().list)

    /**
     * Create a webhook for this channel.
     *
     * @param webhook The webhook to create.
     *
     * @return The created webhook.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createWebhook(webhook: CreateWebhook) =
        postRequest("/channels/$channelId/webhooks", webhook, CreateWebhook.serializer(), Webhook.serializer())
}
