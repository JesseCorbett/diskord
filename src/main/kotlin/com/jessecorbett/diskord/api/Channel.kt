package com.jessecorbett.diskord.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.jessecorbett.diskord.api.models.Overwrite
import com.jessecorbett.diskord.api.models.User
import java.time.ZonedDateTime

data class Channel(
        @JsonProperty("id") val id: String,
        @JsonProperty("type") val type: ChannelType,
        @JsonProperty("guild_id") val guildId: String?,
        @JsonProperty("position") val position: Int?,
        @JsonProperty("permission_overwrites") val permissionOverwrites: Array<Overwrite> = emptyArray(),
        @JsonProperty("name") val name: String?,
        @JsonProperty("topic") val topic: String?,
        @JsonProperty("nsfw") val nsfw: Boolean?,
        @JsonProperty("last_message_id") val lastMessageId: String?,
        @JsonProperty("bitrate") val voiceBitrate: Int?,
        @JsonProperty("user_limit") val voiceUserLimit: Int?,
        @JsonProperty("recipients") val recipients: Array<User>?,
        @JsonProperty("icon") val icon: String?,
        @JsonProperty("owner_id") val ownerId: String?,
        @JsonProperty("application_id") val applicationId: String?,
        @JsonProperty("parent_id") val parentId: String?,
        @JsonProperty("last_pin_timestamp") val lastPinTime: ZonedDateTime?
)

enum class ChannelType(@JsonValue val code: Int) {
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4)
}
