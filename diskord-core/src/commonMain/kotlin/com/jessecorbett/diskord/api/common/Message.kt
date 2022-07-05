package com.jessecorbett.diskord.api.common

import com.jessecorbett.diskord.api.interaction.InteractionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Message(
    @SerialName("id") val id: String,
    @SerialName("channel_id") val channelId: String,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("author") val author: User,
    @SerialName("member") val partialMember: GuildMember? = null,
    @SerialName("content") val content: String,
    @SerialName("timestamp") val sentAt: String,
    @SerialName("edited_timestamp") val editedAt: String?,
    @SerialName("tts") val isTTS: Boolean,
    @SerialName("mention_everyone") val mentionsEveryone: Boolean,
    @SerialName("mentions") val usersMentioned: List<User> = emptyList(),
    @SerialName("mention_roles") val rolesIdsMentioned: List<String> = emptyList(),
    @SerialName("attachments") val attachments: List<Attachment> = emptyList(),
    @SerialName("embeds") val embeds: List<Embed> = emptyList(),
    @SerialName("reactions") val reactions: List<Reaction> = emptyList(),
    @SerialName("nonce") val validationNonce: String? = null,
    @SerialName("pinned") val isPinned: Boolean,
    @SerialName("webhook_id") val webhookId: String? = null,
    @SerialName("type") val type: MessageType,
    @SerialName("activity") val activity: MessageActivity? = null,
    @SerialName("application") val application: MessageApplication? = null,
    @SerialName("message_reference") val reference: MessageReference? = null,
    @SerialName("flags") val flags: Int? = null,
    @SerialName("interaction") val interaction: MessageInteraction? = null,
    @SerialName("thread") val thread: Channel? = null,
    @SerialName("components") val components: List<MessageComponent> = emptyList(),
    @SerialName("stickers_items") val stickerList: List<PartialSticker> = emptyList(),
    @SerialName("stickers") @Deprecated("Deprecated in the Discord API. Use stickerList instead.", ReplaceWith("stickerList")) val stickers: List<MessageSticker> = emptyList(),
)

@Serializable
public enum class MessageType {
    @SerialName("0") DEFAULT,
    @SerialName("1") RECIPIENT_ADD,
    @SerialName("2") RECIPIENT_REMOVE,
    @SerialName("3") CALL,
    @SerialName("4") CHANNEL_NAME_CHANGE,
    @SerialName("5") CHANNEL_ICON_CHANGE,
    @SerialName("6") CHANNEL_PINNED_MESSAGE,
    @SerialName("7") GUILD_MEMBER_JOIN,
    @SerialName("8") USER_PREMIUM_GUILD_SUBSCRIPTION,
    @SerialName("9") USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_1,
    @SerialName("10") USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_2,
    @SerialName("11") USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_3,
    @SerialName("12") CHANNEL_FOLLOW_ADD,
    @SerialName("14") GUILD_DISCOVERY_DISQUALIFIED,
    @SerialName("15") GUILD_DISCOVERY_REQUALIFIED,
    @SerialName("16") GUILD_DISCOVERY_GRACE_PERIOD_INITIAL_WARNING,
    @SerialName("17") GUILD_DISCOVERY_GRACE_PERIOD_FINAL_WARNING,
    @SerialName("18") THREAD_CREATED,
    @SerialName("19") REPLY,
    @SerialName("20") APPLICATION_COMMAND,
    @SerialName("21") THREAD_STARTER_MESSAGE,
    @SerialName("22") GUILD_INVITE_REMINDER,
    @SerialName("23") CONTEXT_MENU_COMMAND,
}

@Serializable
public data class MessageActivity(
    @SerialName("type") val type: MessageActivityType,
    @SerialName("party_id") val partyId: String? = null
)

@Serializable
public enum class MessageActivityType {
    @SerialName("1") JOIN,
    @SerialName("2") SPECTATE,
    @SerialName("3") LISTEN,
    @SerialName("5") JOIN_REQUEST
}

@Serializable
public data class MessageApplication(
    @SerialName("id") val id: String,
    @SerialName("cover_image") val coverImage: String? = null,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String?,
    @SerialName("name") val name: String
)

@Serializable
public data class MessageReference(
    @SerialName("message_id") val messageId: String? = null,
    @SerialName("channel_id") val channelId: String? = null,
    @SerialName("guild_id") val guildId: String? = null
)

@Deprecated("Use Sticker instead.", replaceWith = ReplaceWith("Sticker"))
@Serializable
public data class MessageSticker(
    @SerialName("id") val id: String,
    @SerialName("pack_id") val packId: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("tags") val tags: String? = null,
    @SerialName("asset") val hash: String,
    @SerialName("preview_asset") val previewHash: String?,
    @SerialName("format_type") val formatType: StickerFormat
)

/**
 * The interaction that triggered this message, if the original interaction does not have a message of its own
 * So MessageComponent interactions don't have this property, since they require an existing message
 *
 * https://discord.com/developers/docs/interactions/receiving-and-responding#message-interaction-object
 */
@Serializable
public data class MessageInteraction(
    @SerialName("id") val id: String,
    @SerialName("type") val type: InteractionType,
    @SerialName("name") val commandName: String,
    @SerialName("user") val invokingUser: User,
    @SerialName("member") val guildMember: GuildMember? = null
)

@Serializable
public sealed class MessageComponent(
    @SerialName("type") public val type: Int
)

@Serializable
public data class ActionRow(
    @SerialName("components") public val components: List<MessageComponent>
) : MessageComponent(1)

@Serializable
public data class Button(
    @SerialName("custom_id") public val customId: String? = null,
    @SerialName("disabled") public val disabled: Boolean = false,
    @SerialName("style") public val style: ButtonStyle,
    @SerialName("label") public val label: String? = null,
    @SerialName("emoji") public val emoji: PartialEmoji? = null,
    @SerialName("url") public val url: String? = null,
) : MessageComponent(2)

@Serializable
public data class SelectMenu(
    @SerialName("custom_id") public val customId: String,
    @SerialName("disabled") public val disabled: Boolean = false,
    @SerialName("options") public val options: List<SelectOption>,
    @SerialName("placeholder") public val placeholder: String? = null,
    @SerialName("min_values") public val minValues: Int = 1,
    @SerialName("max_values") public val maxValues: Int = 1,
) : MessageComponent(3)


@Serializable
public enum class ButtonStyle {
    @SerialName("1")
    Primary,
    @SerialName("2")
    Secondary,
    @SerialName("3")
    Success,
    @SerialName("4")
    Danger,
    @SerialName("5")
    Link
}

@Serializable
public data class SelectOption(
    @SerialName("label") public val label: String,
    @SerialName("value") public val value: String,
    @SerialName("description") public val description: String? = null,
    @SerialName("emoji") public val emoji: PartialEmoji? = null,
    @SerialName("default") public val default: Boolean = false
)
