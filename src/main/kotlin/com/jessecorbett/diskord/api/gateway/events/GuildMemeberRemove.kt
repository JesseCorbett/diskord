package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.User

data class GuildMemeberRemove(
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("user") val user: User
)