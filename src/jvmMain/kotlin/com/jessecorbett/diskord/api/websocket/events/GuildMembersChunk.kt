package com.jessecorbett.diskord.api.websocket.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.model.GuildMember

data class GuildMembersChunk(
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("members") val members: List<GuildMember>
)
