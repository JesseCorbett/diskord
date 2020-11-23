package com.jessecorbett.diskord.api.common

import kotlinx.serialization.*

@Serializable
data class Channel(
    @SerialName("id") val id: String,
    @SerialName("type") val type: ChannelType,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("position") var position: Int? = null,
    @SerialName("permission_overwrites") var permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") var name: String? = null,
    @SerialName("topic") var topic: String? = null,
    @SerialName("nsfw") var nsfw: Boolean? = null,
    @SerialName("last_message_id") var lastMessageId: String? = null,
    @SerialName("bitrate") var voiceBitrate: Int? = null,
    @SerialName("user_limit") var voiceUserLimit: Int? = null,
    @SerialName("rate_limit_per_user") var rateLimitPerUser: Int? = null,
    @SerialName("recipients") var dmRecipients: List<User>? = null,
    @SerialName("icon") var iconHash: String? = null,
    @SerialName("owner_id") var dmOwnerId: String? = null,
    @SerialName("application_id") var applicationId: String? = null,
    @SerialName("parent_id") var parentId: String? = null,
    @SerialName("last_pin_timestamp") var lastPinTime: String? = null
)

@Serializable
enum class ChannelType {
    @SerialName("0") GUILD_TEXT,
    @SerialName("1") DM,
    @SerialName("2") GUILD_VOICE,
    @SerialName("3") GROUP_DM,
    @SerialName("4") GUILD_CATEGORY
}

@Serializable
data class Overwrite(
    @SerialName("id") val id: String,
    @SerialName("type") val type: OverwriteType,
    @SerialName("allow") val allowed: Permissions,
    @SerialName("deny") val denied: Permissions
)

@Serializable
enum class OverwriteType {
    @SerialName("role") ROLE,
    @SerialName("member") MEMBER
}
