package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberUpdate(
        @SerialName("guild_id") val guildId: String,
        @SerialName("roles") val roles: List<String>,
        @SerialName("user") val user: User,
        @SerialName("nick") val nickname: String?
)
