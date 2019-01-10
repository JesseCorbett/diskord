package com.jessecorbett.diskord.api.websocket.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.model.User

data class GuildMemberRemove(
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("user") val user: User
)