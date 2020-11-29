package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.GuildMember
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildMembersChunk(
    @SerialName("guild_id") val guildId: String,
    @SerialName("members") val members: List<GuildMember>
)
