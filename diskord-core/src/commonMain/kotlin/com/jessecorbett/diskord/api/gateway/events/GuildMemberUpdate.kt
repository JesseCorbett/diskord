package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildMemberUpdate(
    @SerialName("guild_id") val guildId: String,
    @SerialName("roles") val roles: List<String>,
    @SerialName("user") val user: User,
    @SerialName("nick") val nickname: String? = null,
    @SerialName("joined_at") val joinedAt: String,
    @SerialName("premium_since") val boostedGuildSince: String? = null,
    @SerialName("pending") val pendingMembershipScreening: Boolean? = null
)
