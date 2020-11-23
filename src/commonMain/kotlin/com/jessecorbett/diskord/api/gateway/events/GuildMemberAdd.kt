package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [com.jessecorbett.diskord.api.common.GuildMember] plus guild_id
 */
@Serializable
data class GuildMemberAdd(
    @SerialName("user") val user: User? = null,
    @SerialName("nick") val nickname: String? = null,
    @SerialName("roles") val roleIds: List<String>,
    @SerialName("joined_at") val joinedAt: String,
    @SerialName("deaf") val isDeaf: Boolean,
    @SerialName("mute") val isMute: Boolean,
    @SerialName("guild_id") val guildId: String
)
