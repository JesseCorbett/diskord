package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.models.User

data class GuildMemberUpdate(
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("roles") val roles: Array<String>,
        @JsonProperty("user") val user: User,
        @JsonProperty("nick") val nickname: String?
)
