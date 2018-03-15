package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.User
import java.time.Instant

data class GuildMemberAdd(
        @JsonProperty("user") val user: User,
        @JsonProperty("nick") val nickname: String? = null,
        @JsonProperty("roles") val roleIds: Array<String>,
        @JsonProperty("joined_at") val joinedAt: Instant,
        @JsonProperty("deaf") val isDeaf: Boolean,
        @JsonProperty("mute") val isMute: Boolean,
        @JsonProperty("guild_id") val guildId: String
)
