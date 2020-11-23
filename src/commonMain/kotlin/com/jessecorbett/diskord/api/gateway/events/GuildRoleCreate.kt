package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.Role
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildRoleCreate(
    @SerialName("guild_id") val guildId: String,
    @SerialName("role") val role: Role
)
