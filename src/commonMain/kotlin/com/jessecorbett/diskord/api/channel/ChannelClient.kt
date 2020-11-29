package com.jessecorbett.diskord.api.channel

import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.internal.urlEncode
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.defaultJson
import io.ktor.client.call.*
import io.ktor.client.request.forms.formData
import io.ktor.http.*
import kotlinx.serialization.encodeToString

/**
 * A REST client for a specific channel and it's content

 * @param channelId The id of the channel
 * @param client The REST client implementation
 */
@OptIn(DiskordInternals::class)
public class ChannelClient(public val channelId: String, client: RestClient) : RestClient by client {

    /**
     * Get this channel.
     *
     * @return This channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun get(): Channel = GET("/channels/$channelId").receive()

    /**
     * Update this channel.
     *
     * @param channel The new channel.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun update(channel: Channel): Channel = PUT("/channels/$channelId") { body = channel }.receive()

    /**
     * Delete this channel. Use with caution, cannot be undone except for DMs.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun delete(): Unit = DELETE("/channels/$channelId").receive<Unit>()

    /**
     * Get messages from this channel.
     *
     * @param limit The max number of messages to return, between 1 and 100. Defaults to 50.
     *
     * @return A list of messages.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getMessages(limit: Int = 50): List<Message> {
        return GET("/channels/$channelId/messages", "?limit=$limit").receive()
    }

    /**
     * Get messages from this channel, around a given message.
     *
     * @param limit The max number of messages to return, between 1 and 100. Defaults to 50.
     * @param messageId The message to get messages around.
     *
     * @return A list of messages around the specified message.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getMessagesAround(limit: Int = 50, messageId: String): List<Message> {
        return GET("/channels/$channelId/messages", "?limit=$limit&around=$messageId").receive()
    }

    /**
     * Get messages from this channel, before a given message.
     *
     * @param limit The max number of messages to return, between 1 and 100. Defaults to 50.
     * @param messageId The message to get messages before.
     *
     * @return A list of messages before the specified message.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getMessagesBefore(limit: Int = 50, messageId: String): List<Message> {
        return GET("/channels/$channelId/messages", "?limit=$limit&before=$messageId").receive()
    }

    /**
     * Get messages from this channel, after a given message.
     *
     * @param limit The max number of messages to return, between 1 and 100. Defaults to 50.
     * @param messageId The message to get messages after.
     *
     * @return A list of messages after the specified message.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getMessagesAfter(limit: Int = 50, messageId: String): List<Message> {
        return GET("/channels/$channelId/messages", "?limit=$limit&after=$messageId").receive()
    }

    /**
     * Get a specific message from this channel.
     *
     * @param messageId The id of the message to get.
     *
     * @return The requested message.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getMessage(messageId: String): Message {
        return GET("/channels/$channelId/messages", "/$messageId").receive()
    }

    /**
     * Create a message in this channel.
     *
     * @param message The message to create.
     *
     * @return The created message.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createMessage(message: CreateMessage): Message {
        return POST("/channels/$channelId/messages") { body = message }.receive()
    }

    /**
     * Create a message in this channel with an attachment.
     *
     * @param message The message to create.
     * @param attachment The attachment to add to the message.
     *
     * @return The created message.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createMessage(message: CreateMessage, attachment: FileData): Message {
        return POST("/channels/$channelId/messages") {
            contentType(ContentType.MultiPart.FormData)
            body = formData {
                append("payload_json", defaultJson.encodeToString(message)) // TODO: Check if this should be omitNulls?
                append("file", attachment.packet, Headers.build {
                    append(
                        HttpHeaders.ContentDisposition,
                        """form-data; name="file"; filename="${attachment.filename}""""
                    )
                })
            }
        }.receive()
    }

    /**
     * Add a reaction to a message.
     *
     * Required for unicode emoji, custom emoji must be formatted.
     *
     * @param messageId The message to react to.
     * @param emojiText The text of the emoji to react with.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun addMessageReaction(messageId: String, emojiText: String) {
        PUT(
            "/channels/$channelId/messages/$messageId/reactions",
            "/${urlEncode(emojiText)}/@me",
            rateKey = "/channels/$channelId/messages/messageId/reactions"
        ).receive<Unit>()
    }

    /**
     * Add a reaction to a message.
     *
     * @param messageId The message to react to.
     * @param emoji The custom emoji to react with.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun addMessageReaction(messageId: String, emoji: Emoji) {
        addMessageReaction(messageId, emoji.stringified)
    }

    /**
     * Remove a reaction from a message.
     *
     * Required for unicode emoji, custom emoji must be formatted.
     *
     * @param messageId The message to remove the reaction from.
     * @param emojiText The text of the emoji to remove.
     * @param userId The user whose reaction to remove. Defaults to the current user's reaction.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun removeMessageReaction(messageId: String, emojiText: String, userId: String = "@me") {
        DELETE(
            "/channels/$channelId/messages/$messageId/reactions",
            "/${urlEncode(emojiText)}/$userId",
            rateKey = "/channels/$channelId/messages/messageId/reactions"
        ).receive<Unit>()
    }

    /**
     * Remove a reaction from a message.
     *
     * @param messageId The message to remove the reaction from.
     * @param emoji The custom emoji to remove.
     * @param userId The user whose reaction to remove. Defaults to the current user's reaction.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun removeMessageReaction(messageId: String, emoji: Emoji, userId: String = "@me") {
        removeMessageReaction(messageId, emoji.stringified, userId)
    }

    /**
     * Get all reactions from a message for a given emoji.
     *
     * Required for unicode emoji, custom emoji must be formatted.
     *
     * @param messageId The message to get reactions from.
     * @param textEmoji The text of the emoji to get reactions for.
     *
     * @return The reactions for the given emoji on the given message.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getMessageReactions(messageId: String, textEmoji: String): List<User> {
        return GET(
            "/channels/$channelId/messages/$messageId/reactions",
            "/${urlEncode(textEmoji)}",
            rateKey = "/channels/$channelId/messages/messageId/reactions"
        ).receive()
    }

    /**
     * Get all reactions from a message for a given custom emoji.
     *
     * @param messageId The message to get reactions from.
     * @param emoji The custom emoji to get reactions for.
     *
     * @return The reactions for the given emoji on the given message.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getMessageReactions(messageId: String, emoji: Emoji): List<User> {
        return getMessageReactions(messageId, emoji.stringified)
    }

    /**
     * Delete all reactions from a message.
     *
     * @param messageId The message to remove reactions from.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deleteAllMessageReactions(messageId: String) {
        DELETE(
            "/channels/$channelId/messages/$messageId/reactions",
            rateKey = "/channels/$channelId/messages/messageId/reactions"
        ).receive<Unit>()
    }

    /**
     * Edit a message in this channel.
     *
     * @param messageId The message to edit.
     * @param messageEdit The edits to make.
     *
     * @return The edited message.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun editMessage(messageId: String, messageEdit: MessageEdit): Message {
        return PATCH("/channels/$channelId/messages", "/$messageId") { body = messageEdit }.receive()
    }

    /**
     * Delete a message in this channel.
     *
     * @param messageId The message to delete.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deleteMessage(messageId: String) {
        DELETE("/channels/$channelId/messages", "/$messageId").receive<Unit>()
    }

    /**
     * Bulk delete messages in this channel.
     *
     * @param bulkMessageDelete The messages to delete.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun bulkDeleteMessages(bulkMessageDelete: BulkMessageDelete): BulkMessageDelete {
        return POST("/channels/$channelId/messages/bulk-delete") { body = bulkMessageDelete }.receive()
    }

    /**
     * Edit the permissions for this channel.
     *
     * @param overwrite The updated permissions.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun editPermissions(overwrite: Overwrite) {
        PUT("/channels/$channelId/permissions", "/${overwrite.id}").receive<Unit>()
    }

    /**
     * Get the invites for this channel.
     *
     * @return The list of invites for this channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getInvites(): List<Invite> = GET("/channels/$channelId/invites").receive()

    /**
     * Create an invite for this channel.
     *
     * @param createInvite The invite to create.
     *
     * @return The created invite.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createInvite(createInvite: CreateInvite): Invite {
        return POST("/channels/$channelId/invites") { body = createInvite }.receive()
    }

    /**
     * Delete a permissions set for this channel.
     *
     * @param overwriteId The permissions set to delete.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deletePermissions(overwriteId: String) {
        DELETE("/channels/$channelId/permissions", "/$overwriteId").receive<Unit>()
    }

    /**
     * Indicate that the current user is typing in this channel.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun triggerTypingIndicator() {
        POST("/channels/$channelId/typing").receive<Unit>()
    }

    /**
     * Get the pinned messages from this channel.
     *
     * @return The pinned messages.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getPinnedMessages(): List<Message> = GET("/channels/$channelId/pins").receive()

    /**
     * Pin a message in this channel.
     *
     * @param messageId The message to pin.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun pinMessage(messageId: String) {
        PUT("/channels/$channelId/pins", "/$messageId").receive<Unit>()
    }

    /**
     * Unpin a message in this channel.
     *
     * @param messageId The message to unpin.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun unpinMessage(messageId: String) {
        PUT("/channels/$channelId/pins", "/$messageId").receive<Unit>()
    }

    /**
     * Add a user to this group DM channel.
     *
     * Requires this channel be a group DM and you have the user's OAuth access token.
     *
     * @param userId The user to add.
     * @param groupDMAddRecipient The user access token and optional nickname.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun addGroupDMRecipient(userId: String, groupDMAddRecipient: GroupDMAddRecipient) {
        PUT("/channels/$channelId/recipients", "/$userId") { body = groupDMAddRecipient }.receive<Unit>()
    }

    /**
     * Remove a user from this group DM.
     *
     * Requires this channel be a group DM.
     *
     * @param userId The user to remove.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun removeGroupDMRecipient(userId: String) {
        DELETE("/channels/$channelId/recipients", "/$userId").receive<Unit>()
    }

    /**
     * Get the webhooks in this channel.
     *
     * @return The list webhooks present.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getWebhooks(): List<Webhook> = GET("/channels/$channelId/webhooks").receive()

    /**
     * Create a webhook for this channel.
     *
     * @param webhook The webhook to create.
     *
     * @return The created webhook.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createWebhook(webhook: CreateWebhook): Webhook {
        return POST("/channels/$channelId/webhooks") { body = webhook }.receive()
    }
}
