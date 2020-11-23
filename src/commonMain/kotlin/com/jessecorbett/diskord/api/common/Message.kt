package com.jessecorbett.diskord.api.common

import kotlinx.serialization.*

@Serializable
data class Message(
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
    @SerialName("application") val application: MessageApplication? = null
)

@Serializable
enum class MessageType {
    @SerialName("0") DEFAULT,
    @SerialName("1") RECIPIENT_ADD,
    @SerialName("2") RECIPIENT_REMOVE,
    @SerialName("3") CALL,
    @SerialName("4") CHANNEL_NAME_CHANGE,
    @SerialName("5") CHANNEL_ICON_CHANGE,
    @SerialName("6") CHANNEL_PINNED_MESSAGE,
    @SerialName("7") GUILD_MEMBER_JOIN
}

@Serializable
data class MessageActivity(
    @SerialName("type") val type: MessageActivityType,
    @SerialName("party_id") val partyId: String
)

@Serializable
enum class MessageActivityType {
    @SerialName("0") JOIN,
    @SerialName("1") SPECTATE,
    @SerialName("2") LISTEN,
    @SerialName("3") JOIN_REQUEST
}

@Serializable
data class MessageApplication(
    @SerialName("id") val id: String,
    @SerialName("cover_image") val coverImage: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String,
    @SerialName("name") val name: String
)
