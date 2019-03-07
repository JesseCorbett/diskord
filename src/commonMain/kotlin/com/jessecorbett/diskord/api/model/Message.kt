package com.jessecorbett.diskord.api.model

import kotlinx.serialization.*
import kotlinx.serialization.internal.IntDescriptor

@Serializable
data class Message(
    @SerialName("id") val id: String,
    @SerialName("channel_id") val channelId: String,
    @Optional @SerialName("guild_id") val guildId: String? = null,
    @SerialName("author") val author: User,
    @Optional @SerialName("member") val partialMember: GuildMember? = null,
    @SerialName("content") val content: String,
    @SerialName("timestamp") val sentAt: String,
    @SerialName("edited_timestamp") val editedAt: String?,
    @SerialName("tts") val isTTS: Boolean,
    @SerialName("mention_everyone") val mentionsEveryone: Boolean,
    @Optional @SerialName("mentions") val usersMentioned: List<User> = emptyList(),
    @Optional @SerialName("mention_roles") val rolesIdsMentioned: List<String> = emptyList(),
    @Optional @SerialName("attachments") val attachments: List<Attachment> = emptyList(),
    @Optional @SerialName("embeds") val embeds: List<Embed> = emptyList(),
    @Optional @SerialName("reactions") val reactions: List<Reaction> = emptyList(),
    @Optional @SerialName("nonce") val validationNonce: String? = null,
    @SerialName("pinned") val isPinned: Boolean,
    @Optional @SerialName("webhook_id") val webhookId: String? = null,
    @SerialName("type") val type: MessageType,
    @Optional @SerialName("activity") val activity: MessageActivity? = null,
    @Optional @SerialName("application") val application: MessageApplication? = null
)

@Serializable(with = MessageTypeSerializer::class)
enum class MessageType(val code: Int) {
    DEFAULT(0),
    RECIPIENT_ADD(1),
    RECIPIENT_REMOVE(2),
    CALL(3),
    CHANNEL_NAME_CHANGE(4),
    CHANNEL_ICON_CHANGE(5),
    CHANNEL_PINNED_MESSAGE(6),
    GUILD_MEMBER_JOIN(7)
}

object MessageTypeSerializer : KSerializer<MessageType> {
    override val descriptor: SerialDescriptor = IntDescriptor.withName("MessageTypeSerializer")

    override fun deserialize(decoder: Decoder): MessageType {
        val target = decoder.decodeInt()
        return MessageType.values().first {
            it.code == target
        }
    }

    override fun serialize(encoder: Encoder, obj: MessageType) {
        encoder.encodeInt(obj.code)
    }
}

@Serializable
data class MessageActivity(
    @SerialName("type") val type: MessageActivityType,
    @SerialName("party_id") val partyId: String
)

@Serializable(with = MessageActivityTypeSerializer::class)
enum class MessageActivityType(val code: Int) {
    JOIN(0),
    SPECTATE(1),
    LISTEN(2),
    JOIN_REQUEST(3)
}

object MessageActivityTypeSerializer : KSerializer<MessageActivityType> {
    override val descriptor: SerialDescriptor = IntDescriptor.withName("MessageActivityTypeSerializer")

    override fun deserialize(decoder: Decoder): MessageActivityType {
        val target = decoder.decodeInt()
        return MessageActivityType.values().first {
            it.code == target
        }
    }

    override fun serialize(encoder: Encoder, obj: MessageActivityType) {
        encoder.encodeInt(obj.code)
    }
}

@Serializable
data class MessageApplication(
    @SerialName("id") val id: String,
    @SerialName("cover_image") val coverImage: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String,
    @SerialName("name") val name: String
)
