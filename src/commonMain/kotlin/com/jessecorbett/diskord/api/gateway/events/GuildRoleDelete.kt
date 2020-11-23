package com.jessecorbett.diskord.api.gateway.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildRoleDelete(
    @SerialName("guild_id") val guildId: String,
    @SerialName("role_id") val roleId: String
)
