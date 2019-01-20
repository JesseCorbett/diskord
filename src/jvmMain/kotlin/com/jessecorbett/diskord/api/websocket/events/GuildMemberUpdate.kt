package com.jessecorbett.diskord.api.websocket.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.model.User

data class GuildMemberUpdate(
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("roles") val roles: List<String>,
        @JsonProperty("user") val user: User,
        @JsonProperty("nick") val nickname: String?
)
