package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.model.User
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [com.jessecorbett.diskord.api.model.GuildMember] plus guild_id
 */
@Serializable
data class GuildMemberAdd(
    @Optional @SerialName("user") val user: User? = null,
    @Optional @SerialName("nick") val nickname: String? = null,
    @SerialName("roles") val roleIds: List<String>,
    @SerialName("joined_at") val joinedAt: String,
    @SerialName("deaf") val isDeaf: Boolean,
    @SerialName("mute") val isMute: Boolean,
    @SerialName("guild_id") val guildId: String
)
