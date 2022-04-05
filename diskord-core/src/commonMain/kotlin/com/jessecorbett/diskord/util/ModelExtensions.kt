package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.channel.*
import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.api.channel.EmbedImage
import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.api.global.GlobalClient
import com.jessecorbett.diskord.api.global.PartialGuild
import com.jessecorbett.diskord.api.guild.GuildClient
import com.jessecorbett.diskord.api.guild.PatchGuildMember
import com.jessecorbett.diskord.api.guild.PatchGuildMemberNickname
import com.jessecorbett.diskord.api.interaction.InteractionClient
import com.jessecorbett.diskord.api.interaction.callback.ChannelMessageWithSource
import com.jessecorbett.diskord.api.interaction.callback.InteractionCommandCallbackDataFlags
import com.jessecorbett.diskord.api.interaction.callback.InteractionResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/*
 * Primitive extensions
 */

/**
 * Shortcut to add a spoiler to text.
 *
 * @return This string wrapped with markdown formatting.
 */
public fun String.withSpoiler(): String = "||$this||"

/**
 * Shortcut to add a italics to text.
 *
 * @return This string wrapped with markdown formatting.
 */
public fun String.withItalics(): String = "*$this*"

/**
 * Shortcut to bold text.
 *
 * @return This string wrapped with markdown formatting.
 */
public fun String.withBold(): String = "**$this**"

/**
 * Shortcut to add an underline to text.
 *
 * @return This string wrapped with markdown formatting.
 */
public fun String.withUnderline(): String = "__${this}__"


/**
 * Shortcut to add a strikethrough to text.
 *
 * @return This string wrapped with markdown formatting.
 */
public fun String.withStrikethrough(): String = "~~$this~~"

/**
 * Shortcut to put the code in a single line code block.
 *
 * @return This string wrapped with markdown formatting.
 */
public fun String.withSingleLineCode(): String = "`$this`"

/**
 * Shortcut to put the code in a multi line code block.
 *
 * @return This string wrapped with markdown formatting.
 */
public fun String.withMultiLineCode(): String = "```$this```"

/**
 * Shortcut to put the code in a multi line code block with a language style.
 *
 * @param language The language to add style for.
 *
 * @return This string wrapped with markdown formatting.
 */
public fun String.withMultiLineCode(language: String): String = "```$language $this```"

/*
 * Message extensions
 */

/**
 * Shortcut to check if a message is from a user.
 */
public val Message.isFromUser: Boolean
    get() = !(author.isBot ?: false) && webhookId == null

/**
 * Shortcut to check if a message is from a bot.
 */
public val Message.isFromBot: Boolean
    get() = (author.isBot ?: false) && this.webhookId == null

/**
 * Shortcut to check if a message is from a webhook.
 */
public val Message.isFromWebhook: Boolean
    get() = webhookId != null


/**
 * Shortcut to get the [User.id] of the [Message.author].
 */
public val Message.authorId: String
    get() = author.id

/**
 * Shortcut to get the [Message.content] split into words.
 */
public val Message.words: List<String>
    get() = content.split(" ")


/*
 * User extensions
 */

/**
 * Convenience method to turn a user id into a formatted mention for chat.
 *
 * @return the user in chat mention format.
 */
public fun String.toUserMention(): String = "<@$this>"

/**
 * Convenience method to turn a user into a formatted mention for chat.
 *
 * @return the user in chat mention format.
 */
public val User.mention: String
    get() = id.toUserMention()

/**
 * Convenience method to check if a user has a custom avatar.
 */
public val User.hasCustomAvatar: Boolean
    get() = avatarHash != null


/*
 * Role extensions
 */
/**
 * Convenience method to turn a role id into a formatted mention for chat.
 *
 * @return the role in chat mention format.
 */
public fun String.toRoleMention(): String = "<@&$this>"

/**
 * Convenience method to turn a role into a formatted mention for chat.
 *
 * @return the role in chat mention format.
 */
public val Role.mention: String
    get() = id.toRoleMention()


/*
 * Channel extensions
 */
/**
 * Convenience method to turn a channel into a formatted mention for chat.
 *
 * @return the channel in chat mention format.
 */
public val GuildText.mention: String
    get() = "#$name"


/*
 * Emoji extensions
 */

/**
 * Convenience method to check if an emoji is a Unicode emoji.
 */
public val Emoji.isUnicode: Boolean
    get() = id == null

/**
 * Convenience method to check if an emoji is a custom emoji.
 */
public val Emoji.isCustom: Boolean
    get() = !isUnicode

/**
 * Convenience method to convert an emoji object into a chat formatted emoji.
 *
 * @return the emoji in chat format.
 */
public val Emoji.tag: String
    get() = if (isUnicode) name!! else "<${if (isAnimated) "a" else ""}:$name:$id>"


/*
 * Client extensions
 */

/**
 * Calls [ChannelClient.createMessage] for text messages without needing to create a [CreateMessage] object first.
 *
 * @param message The text message to send.
 * @param embed The embed to include with the message.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
public suspend fun ChannelClient.sendMessage(message: String = "", embed: Embed? = null): Message {
    return createMessage(CreateMessage(content = message, embed = embed))
}

/**
 * Calls [ChannelClient.createMessage] for embedded messages without needing to create a [CreateMessage] object first.
 * Also accepts a lambda that can be used to configure an [Embed] object.
 *
 * @param message The text message to send.
 * @param block The block to configure the [Embed] object with.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
public suspend fun ChannelClient.sendEmbed(
    message: String = "",
    block: Embed.() -> Unit
): Message {
    return createMessage(CreateMessage(message, embed = Embed().apply { block() }))
}

/**
 * Calls [ChannelClient.createMessage] for embedded messages and images without needing to create a [CreateMessage]
 * object first. Also accepts a lambda that can be used to configure an [Embed] object.
 *
 * @param message The text message to send.
 * @param image The image to embed.
 * @param block The block to configure the [Embed] object with.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
public suspend fun ChannelClient.sendEmbeddedImage(
    message: String = "",
    image: FileData,
    block: Embed.() -> Unit
): Message {
    return createMessage(
        CreateMessage(message, embed = Embed().apply {
            block()
            this.image = EmbedImage(url = "attachment://${image.filename}")
        }),
        image
    )
}

/**
 * Calls [ChannelClient.createMessage] to reply to a specific text message without needing to create a [CreateMessage]
 * object first.
 *
 * @param message The message to reply to.
 * @param reply The text reply message to send.
 * @param embed The embed to include with the message.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
public suspend fun ChannelClient.sendReply(message: Message, reply: String = "", embed: Embed? = null): Message {
    return createMessage(
        CreateMessage(content = reply, embed = embed, messageReference = MessageReference(messageId = message.id))
    )
}

/**
 * Calls [ChannelClient.createMessage] to reply to a specific text message with an embedded message without needing to
 * create a [CreateMessage] object first. Also accepts a lambda that can be used to configure an [Embed] object.
 *
 * @param message The message to reply to.
 * @param reply The text reply message to send.
 * @param block The block to configure the [Embed] object with.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
public suspend fun ChannelClient.sendEmbeddedReply(
    message: Message,
    reply: String = "",
    block: Embed.() -> Unit
): Message {
    return createMessage(
        CreateMessage(
            content = reply,
            embed = Embed().apply { block() },
            messageReference = MessageReference(messageId = message.id)
        )
    )
}

/**
 * Calls [ChannelClient.createMessage] to reply to a specific text message with an embedded message and image without
 * needing to create a [CreateMessage] object first. Also accepts a lambda that can be used to configure an [Embed]
 * object.
 *
 * @param message The message to reply to.
 * @param reply The text reply message to send.
 * @param image The image to embed.
 * @param block The block to configure the [Embed] object with.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
public suspend fun ChannelClient.sendEmbeddedImageReply(
    message: Message,
    reply: String = "",
    image: FileData,
    block: Embed.() -> Unit
): Message {
    return createMessage(
        CreateMessage(content = reply, embed = Embed().apply {
            block()
            this.image = EmbedImage(url = "attachment://${image.filename}")
        }, messageReference = MessageReference(messageId = message.id)),
        image
    )
}

/**
 * Calls [ChannelClient.createMessage] for to attach a file without needing to create a [CreateMessage] object first.
 *
 * @param data The file to attach.
 * @param comment An optional comment to send with the file.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
public suspend fun ChannelClient.sendFile(data: FileData, comment: String = ""): Message {
    return createMessage(CreateMessage(content = comment), data)
}

/**
 * Adds a reaction to the specified message.
 *
 *  @param messageId The ID of the message to react to.
 *  @param emojiId The ID of the emoji.
 *  @param emojiName The name of the emoji.
 *
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
public suspend fun ChannelClient.addMessageReaction(messageId: String, emojiId: String, emojiName: String) {
    addMessageReaction(messageId, Emoji(emojiId, emojiName))
}

/**
 * Changes the user's nickname in this client's guild.
 *
 * @param nickname the new nickname.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
public suspend fun GuildClient.changeNickname(nickname: String) {
    changeMemberNickname(PatchGuildMemberNickname(nickname))
}

/**
 * Changes a user's nickname in this client's guild.
 *
 * @param userId the id of the user whose nickname to change.
 * @param nickname the new nickname.
 *
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
public suspend fun GuildClient.changeNickname(userId: String, nickname: String) {
    updateMember(userId, PatchGuildMember(nickname))
}

/**
 * Create a thread from an existing message.
 *
 * Only usable for [GuildTextChannel] or [GuildNewsChannel].
 *
 * @param message The message to attach the thread to.
 * @param createThread The thread to create.
 *
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
 */
public suspend fun ChannelClient.createThreadFromMessage(message: Message, createThread: CreateThread): GuildThread =
    createThreadFromMessage(message.id, createThread)

/**
 * Add a user to the current thread.
 *
 * Only usable for [GuildThread].
 *
 * @param user The user to add to the thread.
 *
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
 */
public suspend fun ChannelClient.addThreadMember(user: User): Unit =
    addThreadMember(user.id)

/**
 * Add a user to the current thread.
 *
 * Only usable for [GuildThread].
 *
 * @param member The user to add to the thread.
 *
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
 */
public suspend fun ChannelClient.addThreadMember(member: ThreadMember): Unit =
    addThreadMember(requireNotNull(member.userId) { "member.userId must not be null" })

/**
 * Add a user to the current thread.
 *
 * Only usable for [GuildThread].
 *
 * @param member The user to add to the thread.
 *
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
 */
public suspend fun ChannelClient.addThreadMember(member: GuildMember): Unit =
    addThreadMember(requireNotNull(member.user) { "member.user must not be null" }.id)

/**
 * Remove a user from the current thread.
 *
 * Only usable for [GuildThread].
 *
 * Requires [Permission.MANAGE_THREADS] permission if the thread is not private or current user
 * is not the creator of the thread.
 *
 * @param user The user to remove from the thread.
 *
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
 */
public suspend fun ChannelClient.removeThreadMember(user: User): Unit =
    removeThreadMember(user.id)

/**
 * Remove a user from the current thread.
 *
 * Only usable for [GuildThread].
 *
 * Requires [Permission.MANAGE_THREADS] permission if the thread is not private or current user
 * is not the creator of the thread.
 *
 * @param member The user to remove from the thread.
 *
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
 */
public suspend fun ChannelClient.removeThreadMember(member: ThreadMember): Unit =
    removeThreadMember(requireNotNull(member.userId) { "member.userId must not be null" })

/**
 * Remove a user from the current thread.
 *
 * Only usable for [GuildThread].
 *
 * Requires [Permission.MANAGE_THREADS] permission if the thread is not private or current user
 * is not the creator of the thread.
 *
 * @param member The user to remove from the thread.
 *
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
 */
public suspend fun ChannelClient.removeThreadMember(member: GuildMember): Unit =
    removeThreadMember(requireNotNull(member.user) { "member.user must not be null" }.id)

/**
 * Determine if the channel type represents a thread.
 *
 * @return if the channel is a thread
 */
public val ChannelType.isThread: Boolean
    get() = this == ChannelType.GUILD_PUBLIC_THREAD
            || this == ChannelType.GUILD_PRIVATE_THREAD
            || this == ChannelType.GUILD_NEWS_THREAD

/**
 * Fetches all guilds from the [GuildClient] over potentially multiple API calls
 */
public suspend fun GlobalClient.getAllGuilds(): List<PartialGuild> {
    val client = this
    val total: MutableList<PartialGuild> = mutableListOf()
    var last: List<PartialGuild> = emptyList()

    do {
        last = client.getGuilds(200, null, last.lastOrNull()?.id)
        total += last
    } while (last.size == 200)

    return total
}

/**
 * Convenience method for responding to an interaction with a message
 */
public suspend fun InteractionClient.messageResponse(interactionId: String, message: String, ephemeral: Boolean) {
    val data = ChannelMessageWithSource(
        data = ChannelMessageWithSource.Data(
            content = message,
            flags = if (ephemeral) InteractionCommandCallbackDataFlags.EPHEMERAL else InteractionCommandCallbackDataFlags.NONE
        )
    )
    println(defaultJson.encodeToString<InteractionResponse>(data))
    createInteractionResponse(interactionId, data)
}
