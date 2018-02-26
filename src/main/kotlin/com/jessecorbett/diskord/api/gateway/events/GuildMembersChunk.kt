package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.models.GuildMember

data class GuildMembersChunk(
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("members") val members: Array<GuildMember>
)
