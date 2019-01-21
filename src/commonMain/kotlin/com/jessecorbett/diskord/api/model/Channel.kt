package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Channel(
    @SerialName("id") val id: String,
    @SerialName("type") val type: ChannelType,
    @SerialName("guild_id") val guildId: String?,
    @SerialName("position") var position: Int?,
    @SerialName("permission_overwrites") var permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") var name: String?,
    @SerialName("topic") var topic: String?,
    @SerialName("nsfw") var nsfw: Boolean?,
    @SerialName("last_message_id") var lastMessageId: String?,
    @SerialName("bitrate") var voiceBitrate: Int?,
    @SerialName("user_limit") var voiceUserLimit: Int?,
    @SerialName("recipients") var dmRecipients: List<User>?,
    @SerialName("icon") var iconHash: String?,
    @SerialName("owner_id") var dmOwnerId: String?,
    @SerialName("application_id") var applicationId: String?,
    @SerialName("parent_id") var parentId: String?,
    @SerialName("last_pin_timestamp") var lastPinTime: String?
)

enum class ChannelType(val code: Int) {
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4)
}

@Serializable
data class Overwrite(
        @SerialName("id") val id: String,
        @SerialName("type") val type: OverwriteType,
        @SerialName("allow") val allowed: Int,// TODO: Parse int bit set
        @SerialName("deny") val denied: Int
)

enum class OverwriteType(val value: String) {
    ROLE("role"),
    MEMBER("member")
}
