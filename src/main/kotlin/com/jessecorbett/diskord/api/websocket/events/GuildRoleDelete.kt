package com.jessecorbett.diskord.api.websocket.events

import com.fasterxml.jackson.annotation.JsonProperty

data class GuildRoleDelete(
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("role_id") val roleId: String
)
