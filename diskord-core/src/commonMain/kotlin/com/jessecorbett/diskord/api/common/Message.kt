package com.jessecorbett.diskord.api.common

import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.api.interaction.InteractionType
import com.jessecorbett.diskord.internal.CodeEnum
import com.jessecorbett.diskord.internal.CodeEnumSerializer
import com.jessecorbett.diskord.internal.MessageComponentSerializer
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
)

@Serializable(with = MessageTypeSerializer::class)
public enum class MessageType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    DEFAULT(0),
    RECIPIENT_ADD(1),
    RECIPIENT_REMOVE(2),
    CALL(3),
    CHANNEL_NAME_CHANGE(4),
    CHANNEL_ICON_CHANGE(5),
    CHANNEL_PINNED_MESSAGE(6),
    GUILD_MEMBER_JOIN(7),
    USER_PREMIUM_GUILD_SUBSCRIPTION(8),
    USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_1(9),
    USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_2(10),
    USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_3(11),
    CHANNEL_FOLLOW_ADD(12),
    GUILD_DISCOVERY_DISQUALIFIED(14),
    GUILD_DISCOVERY_REQUALIFIED(15),
    GUILD_DISCOVERY_GRACE_PERIOD_INITIAL_WARNING(16),
    GUILD_DISCOVERY_GRACE_PERIOD_FINAL_WARNING(17),
    THREAD_CREATED(18),
    REPLY(19),
    APPLICATION_COMMAND(20),
    THREAD_STARTER_MESSAGE(21),
    GUILD_INVITE_REMINDER(22),
    CONTEXT_MENU_COMMAND(23),
}

public class MessageTypeSerializer : CodeEnumSerializer<MessageType>(MessageType.UNKNOWN, MessageType.values())

@Serializable
public data class MessageActivity(
    @SerialName("type") val type: MessageActivityType,
    @SerialName("party_id") val partyId: String? = null
)

@Serializable(with = MessageActivityTypeSerializer::class)
public enum class MessageActivityType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    JOIN(1),
    SPECTATE(2),
    LISTEN(3),
    JOIN_REQUEST(5)
}

public class MessageActivityTypeSerializer : CodeEnumSerializer<MessageActivityType>(MessageActivityType.UNKNOWN, MessageActivityType.values())

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

@Serializable(with = MessageComponentSerializer::class)
public sealed class MessageComponent

@Serializable
public data class ActionRow(
    @SerialName("components") public val components: List<MessageComponent>
) : MessageComponent()

@Serializable
public data class Button(
    @SerialName("custom_id") public val customId: String? = null,
    @SerialName("disabled") public val disabled: Boolean = false,
    @SerialName("style") public val style: ButtonStyle,
    @SerialName("label") public val label: String? = null,
    @SerialName("emoji") public val emoji: PartialEmoji? = null,
    @SerialName("url") public val url: String? = null,
) : MessageComponent()

@Serializable
public data class SelectMenu(
    @SerialName("custom_id") public val customId: String,
    @SerialName("disabled") public val disabled: Boolean = false,
    @SerialName("options") public val options: List<SelectOption>,
    @SerialName("placeholder") public val placeholder: String? = null,
    @SerialName("min_values") public val minValues: Int = 1,
    @SerialName("max_values") public val maxValues: Int = 1,
) : MessageComponent()

@Serializable(with = ButtonStyleSerializer::class)
public enum class ButtonStyle(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    Primary(1),
    Secondary(2),
    Success(3),
    Danger(4),
    Link(5)
}

public class ButtonStyleSerializer : CodeEnumSerializer<ButtonStyle>(ButtonStyle.UNKNOWN, ButtonStyle.values())

@Serializable
public data class SelectOption(
    @SerialName("label") public val label: String,
    @SerialName("value") public val value: String,
    @SerialName("description") public val description: String? = null,
    @SerialName("emoji") public val emoji: PartialEmoji? = null,
    @SerialName("default") public val default: Boolean = false
)
