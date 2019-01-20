package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class GuildMember(
        @JsonProperty("user") val user: User,
        @JsonProperty("nick") val nickname: String? = null,
        @JsonProperty("roles") val roleIds: List<String>,
        @JsonProperty("joined_at") val joinedAt: Instant,
        @JsonProperty("deaf") val isDeaf: Boolean,
        @JsonProperty("mute") val isMute: Boolean
)
