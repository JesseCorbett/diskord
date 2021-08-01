package com.jessecorbett.diskord.api.channel

import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.api.gateway.model.GatewayIntent
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.internal.urlEncode
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.defaultJson
import com.jessecorbett.diskord.util.isThread
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
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
    public suspend fun getChannel(): Channel = GET("/channels/$channelId").receive()

    /**
     * Update this channel's name.
     *
     * Not available for 1:1 DMs.
     *
     * @param name The new channel name.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateName(name: String): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchChannelName(name)
        }.receive()
    }

    /**
     * Update this channel's type.
     *
     * Can only be performed to change [GuildTextChannel] to [GuildNewsChannel] and vice versa.
     *
     * @param type The new channel type.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateType(type: ChannelType): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchChannelType(type)
        }.receive()
    }

    /**
     * Update this channel's position.
     *
     * Can only be performed on [GuildChannel].
     *
     * @param position The new channel position.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updatePosition(position: Int?): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchChannelPosition(position)
        }.receive()
    }

    /**
     * Update this channel's topic.
     *
     * Can only be performed on [GuildTextChannel].
     *
     * @param topic The new channel topic.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateTopic(topic: String?): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchChannelTopic(topic)
        }.receive()
    }

    /**
     * Update if this channel is NSFW.
     *
     * Can only be performed on [GuildChannel].
     *
     * @param isNSFW Whether the channel is NSFW.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateNSFW(isNSFW: Boolean): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchChannelNSFW(isNSFW)
        }.receive()
    }

    /**
     * Update the channel's per-user rate limit.
     *
     * Can only be performed on [GuildTextChannel].
     *
     * @param rateLimit Amount of seconds a user has to wait before sending another message.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateUserRateLimit(rateLimit: Int?): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchChannelUserRateLimit(rateLimit)
        }.receive()
    }

    /**
     * Update the channel's bitrate.
     *
     * Can only be performed on [GuildVoiceChannel].
     *
     * @param bitrate The bitrate, 8000 to 96000 (128000 for VIP servers).
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateBitrate(bitrate: Int?): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchChannelBitrate(bitrate)
        }.receive()
    }

    /**
     * Update the channel's user limit.
     *
     * Can only be performed on [GuildVoiceChannel].
     *
     * @param userLimit The max number of users allowed in the channel. 0 for unlimited.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateUserLimit(userLimit: Int?): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchChannelUserLimit(userLimit)
        }.receive()
    }

    /**
     * Update the channel's permissions.
     *
     * Can only be performed on [GuildChannel].
     *
     * @param permissionsOverwrites The new permissions for the channel.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updatePermissions(permissionsOverwrites: List<Overwrite>): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchChannelOverwrites(permissionsOverwrites)
        }.receive()
    }

    /**
     * Update the channel's parent.
     *
     * Can only be performed on [GuildChannel].
     *
     * @param parentId The ID of the new parent [GuildCategory].
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateParentChannel(parentId: String?): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchChannelParent(parentId)
        }.receive()
    }

    /**
     * Update the channel's voice region.
     *
     * Can only be performed on [GuildVoiceChannel].
     *
     * @param regionId The ID of the new [VoiceRegion].
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateRtcRegion(regionId: String?): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchRtcRegion(regionId)
        }.receive()
    }

    /**
     * Update the channel's video quality mode.
     *
     * Can only be performed on [GuildVoiceChannel].
     *
     * @param videoQualityMode The new [VideoQualityMode].
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateVideoQualityMode(videoQualityMode: VideoQualityMode?): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchVideoQualityMode(videoQualityMode)
        }.receive()
    }

    /**
     * Update the channel's default duration for newly created threads to automatically archive after recent activity.
     *
     * Can only be performed on [GuildTextChannel] or [GuildNewsChannel].
     *
     * @param defaultAutoArchiveDuration The archive duration, in minutes.
     *
     * @return The updated channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateDefaultAutoArchiveDuration(defaultAutoArchiveDuration: Int?): Channel {
        return PATCH("/channels/$channelId") {
            body = PatchDefaultAutoArchiveDuration(defaultAutoArchiveDuration)
        }.receive()
    }

    /**
     * Delete this channel, or closes it if it is a [DM].
     *
     * Use with caution, cannot be undone except for DMs.
     * Deleting a [GuildCategory] does not delete the children.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deleteChannel(): Unit = DELETE("/channels/$channelId").receive()

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
            body = MultiPartFormDataContent(formData {
                append("payload_json", defaultJson.encodeToString(message)) // TODO: Check if this should be omitNulls?
                append("file", attachment.packet, Headers.build {
                    if (attachment.contentType != null) {
                        append(HttpHeaders.ContentType, attachment.contentType)
                    }

                    append(
                        HttpHeaders.ContentDisposition,
                        """form-data; name="file"; filename="${attachment.filename}""""
                    )
                })
            })
        }.receive()
    }

    /**
     * Crosspost a message in a [GuildNewsChannel] to following channels.
     *
     * @param messageId The message to crosspost.
     *
     * @return The crossposted message.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun crosspostMessage(messageId: String): Message {
        return POST("/channels/$channelId/messages/$messageId/crosspost").receive()
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
     * Delete all reactions from a message for a specific emoji.
     *
     * @param messageId The message to remove reactions from.
     * @param textEmoji The text of the emoji to remove.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deleteAllMessageReactions(messageId: String, textEmoji: String) {
        DELETE(
            "/channels/$channelId/messages/$messageId/reactions",
            "/${urlEncode(textEmoji)}",
            rateKey = "/channels/$channelId/messages/messageId/reactions"
        ).receive<Unit>()
    }

    /**
     * Delete all reactions from a message for a specific emoji.
     *
     * @param messageId The message to remove reactions from.
     * @param emoji The custom emoji to remove.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deleteAllMessageReactions(messageId: String, emoji: Emoji) {
        deleteAllMessageReactions(messageId, emoji.stringified)
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
     * Will not delete messages older than 2 weeks.
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
     * Only for [GuildChannel]
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
     * Only usable for [GuildChannel].
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
     * Max 50 pinned messages per channel.
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
     * Create a thread from an existing message.
     *
     * Only usable for [GuildTextChannel] or [GuildNewsChannel].
     *
     * @param messageId The message to attach the thread to.
     * @param createThread The thread to create.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createThreadFromMessage(messageId: String, createThread: CreateThread): GuildThread =
        POST("/channels/$channelId/messages/$messageId/threads") { body = createThread }.receive()

    /**
     * Create a thread not connected to an existing message.
     *
     * Only usable for [GuildTextChannel] or [GuildNewsChannel].
     *
     * @param createThread The thread to create.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createThread(createThread: CreateThreadWithType): GuildThread {
        require(createThread.type.isThread) { "createThread.type must be a thread" }

        return POST("/channels/$channelId/threads") { body = createThread }.receive()
    }

    /**
     * Join the current thread.
     *
     * Only usable for [GuildThread].
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun joinThread(): Unit = PUT("/channels/$channelId/thread-members/@me").receive()

    /**
     * Add a user to the current thread.
     *
     * Only usable for [GuildThread].
     *
     * @param userId The user to add to the thread.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun addThreadMember(userId: String): Unit =
        PUT("/channels/$channelId/thread-members/$userId").receive()

    /**
     * Leave the current thread.
     *
     * Only usable for [GuildThread].
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun leaveThread(): Unit = DELETE("/channels/$channelId/thread-members/@me").receive()

    /**
     * Remove a user from the current thread.
     *
     * Only usable for [GuildThread].
     *
     * Requires [Permission.MANAGE_THREADS] permission if the thread is not private or current user
     * is not the creator of the thread.
     *
     * @param userId The user to remove from the thread.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun removeThreadMember(userId: String): Unit =
        DELETE("/channels/$channelId/thread-members/$userId").receive()

    /**
     * Get a list of members for the current thread.
     *
     * Only usable for [GuildThread].
     *
     * Requires [GatewayIntent.GUILD_MEMBERS] intent.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getThreadMembers(): List<ThreadMember> = GET("/channels/$channelId/thread-members").receive()

    /**
     * Get a list of active threads and associated members for the current channel.
     *
     * Only usable for [GuildTextChannel] or [GuildNewsChannel].
     *
     * Requires [Permission.READ_MESSAGE_HISTORY] permissions.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getActiveThreads(): ActiveThreads = GET("/channels/$channelId/threads/active").receive()

    /**
     * Get a list of public archived threads and associated members for the current channel.
     *
     * Only usable for [GuildTextChannel] or [GuildNewsChannel].
     *
     * Requires [Permission.READ_MESSAGE_HISTORY] and [Permission.MANAGE_THREADS] permissions.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getPublicArchivedThreads(): ArchivedThreads =
        GET("/channels/{channel.id}/threads/archived/public").receive()

    /**
     * Get a list of private archived threads and associated members for the current channel.
     *
     * Only usable for [GuildTextChannel] or [GuildNewsChannel].
     *
     * Requires [Permission.READ_MESSAGE_HISTORY] and [Permission.MANAGE_THREADS] permissions.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getPrivateArchivedThreads(): ArchivedThreads =
        GET("/channels/{channel.id}/threads/archived/private").receive()

    /**
     * Get a list of joined private threads.
     *
     * Only usable for [GuildTextChannel] or [GuildNewsChannel].
     *
     * Requires [Permission.MANAGE_THREADS] permissions.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getJoinedPrivateArchivedThreads(): ArchivedThreads =
        GET("/channels/{channel.id}/threads/archived/private").receive()

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
