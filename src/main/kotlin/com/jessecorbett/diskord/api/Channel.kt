package com.jessecorbett.diskord.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.jessecorbett.diskord.api.models.Overwrite
import java.time.Instant

data class Channel(
        @JsonProperty("id") val id: String,
        @JsonProperty("type") val type: ChannelType,
        @JsonProperty("guild_id") val guildId: String?,
        @JsonProperty("position") var position: Int?,
        @JsonProperty("permission_overwrites") var permissionOverwrites: Array<Overwrite> = emptyArray(),
        @JsonProperty("name") var name: String?,
        @JsonProperty("topic") var topic: String?,
        @JsonProperty("nsfw") var nsfw: Boolean?,
        @JsonProperty("last_message_id") var lastMessageId: String?,
        @JsonProperty("bitrate") var voiceBitrate: Int?,
        @JsonProperty("user_limit") var voiceUserLimit: Int?,
        @JsonProperty("recipients") var dmRecipients: Array<User>?,
        @JsonProperty("icon") var iconHash: String?,
        @JsonProperty("owner_id") var dmOwnerId: String?,
        @JsonProperty("application_id") var applicationId: String?,
        @JsonProperty("parent_id") var parentId: String?,
        @JsonProperty("last_pin_timestamp") var lastPinTime: Instant?
)

enum class ChannelType(@JsonValue val code: Int) {
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4)
}
