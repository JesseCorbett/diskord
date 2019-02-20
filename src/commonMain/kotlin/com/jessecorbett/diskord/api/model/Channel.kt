package com.jessecorbett.diskord.api.model

import kotlinx.serialization.*
import kotlinx.serialization.internal.IntDescriptor
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class Channel(
        @SerialName("id") val id: String,
        @SerialName("type") val type: ChannelType,
        @Optional @SerialName("guild_id") val guildId: String? = null,
        @Optional @SerialName("position") var position: Int? = null,
        @Optional @SerialName("permission_overwrites") var permissionOverwrites: List<Overwrite> = emptyList(),
        @Optional @SerialName("name") var name: String? = null,
        @Optional @SerialName("topic") var topic: String? = null,
        @Optional @SerialName("nsfw") var nsfw: Boolean? = null,
        @Optional @SerialName("last_message_id") var lastMessageId: String? = null,
        @Optional @SerialName("bitrate") var voiceBitrate: Int? = null,
        @Optional @SerialName("user_limit") var voiceUserLimit: Int? = null,
        @Optional @SerialName("rate_limit_per_user") var rateLimitPerUser: Int? = null,
        @Optional @SerialName("recipients") var dmRecipients: List<User>? = null,
        @Optional @SerialName("icon") var iconHash: String? = null,
        @Optional @SerialName("owner_id") var dmOwnerId: String? = null,
        @Optional @SerialName("application_id") var applicationId: String? = null,
        @Optional @SerialName("parent_id") var parentId: String? = null,
        @Optional @SerialName("last_pin_timestamp") var lastPinTime: String? = null
)

@Serializable(with = ChannelTypeSerializer::class)
enum class ChannelType(val code: Int) {
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4)
}

object ChannelTypeSerializer : KSerializer<ChannelType> {
    override val descriptor: SerialDescriptor = IntDescriptor.withName("ChannelTypeSerializer")

    override fun deserialize(decoder: Decoder): ChannelType {
        val target = decoder.decodeInt()
        return ChannelType.values().first {
            it.code == target
        }
    }

    override fun serialize(encoder: Encoder, obj: ChannelType) {
        encoder.encodeInt(obj.code)
    }
}

@Serializable
data class Overwrite(
        @SerialName("id") val id: String,
        @SerialName("type") val type: OverwriteType,
        @SerialName("allow") val allowed: Permissions,
        @SerialName("deny") val denied: Permissions
)

@Serializable(with = OverwriteTypeSerializer::class)
enum class OverwriteType(val value: String) {
    ROLE("role"),
    MEMBER("member")
}

object OverwriteTypeSerializer : KSerializer<OverwriteType> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("OverwriteTypeSerializer")

    override fun deserialize(decoder: Decoder): OverwriteType {
        val target = decoder.decodeString()
        return OverwriteType.values().first {
            it.value == target
        }
    }

    override fun serialize(encoder: Encoder, obj: OverwriteType) {
        encoder.encodeString(obj.value)
    }
}
