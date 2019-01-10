package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.rest.client.ChannelClient
import com.jessecorbett.diskord.api.rest.client.GuildClient
import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.rest.CreateMessage
import com.jessecorbett.diskord.api.rest.PatchGuildMember
import com.jessecorbett.diskord.api.rest.PatchGuildMemberNickname


/*
 * Message extensions
 */

/**
 * Shortcut to check if a message is from a user.
 */
val Message.isFromUser: Boolean
    get() = !author.isBot

/**
 * Shortcut to check if a message is from a bot.
 */
val Message.isFromBot: Boolean
    get() = author.isBot && this.webhookId == null

/**
 * Shortcut to check if a message is from a webhook.
 */
val Message.isFromWebhook: Boolean
    get() = webhookId != null


/**
 * Shortcut to get the [User.id] of the [Message.author].
 */
val Message.authorId: String
    get() = author.id

/**
 * Shortcut to get the [Message.content] split into words.
 */
val Message.words: List<String>
    get() = content.split(" ")


/*
 * User extensions
 */

/**
 * Convenience method to turn a user id into a formatted mention for chat.
 *
 * @return the user in chat mention format.
 */
fun String.toUserMention() = "<@$this>"

/**
 * Convenience method to turn a user into a formatted mention for chat.
 *
 * @return the user in chat mention format.
 */
val User.mention: String
    get() = id.toUserMention()

/**
 * Convenience method to check if a user has a custom avatar.
 */
val User.hasCustomAvatar: Boolean
    get() = avatarHash != null


/*
 * Role extensions
 */
/**
 * Convenience method to turn a role id into a formatted mention for chat.
 *
 * @return the role in chat mention format.
 */
fun String.toRoleMention() = "<@&$this>"

/**
 * Convenience method to turn a role into a formatted mention for chat.
 *
 * @return the role in chat mention format.
 */
val Role.mention: String
    get() = id.toRoleMention()


/*
 * Channel extensions
 */
/**
 * Convenience method to turn a channel into a formatted mention for chat.
 *
 * @return the channel in chat mention format.
 */
val Channel.mention: String
    get() = "#$name"


/*
 * Emoji extensions
 */

/**
 * Convenience method to check if an emoji is a Unicode emoji.
 */
val Emoji.isUnicode: Boolean
    get() = id == null

/**
 * Convenience method to check if an emoji is a custom emoji.
 */
val Emoji.isCustom: Boolean
    get() = !isUnicode

/**
 * Convenience method to convert an emoji object into a chat formatted emoji.
 *
 * @return the emoji in chat format.
 */
val Emoji.tag: String
    get() = if (isUnicode) name else "<${if (isAnimated) "a" else ""}:$name:$id>"


/*
 * Client extensions
 */

/**
 * Calls [ChannelClient.createMessage] for text messages without needing to create a [CreateMessage] object first.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
 */
suspend fun ChannelClient.sendMessage(message: String) = createMessage(CreateMessage(message))

/**
 * Changes the user's nickname in this client's guild.
 *
 * @param nickname the new nickname.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
 */
suspend fun GuildClient.changeNickname(nickname: String) = changeMemberNickname(PatchGuildMemberNickname(nickname))

/**
 * Changes a user's nickname in this client's guild.
 *
 * @param userId the id of the user whose nickname to change.
 * @param nickname the new nickname.
 *
 * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
 */
suspend fun GuildClient.changeNickname(userId: String, nickname: String) = updateMember(userId, PatchGuildMember(nickname))
