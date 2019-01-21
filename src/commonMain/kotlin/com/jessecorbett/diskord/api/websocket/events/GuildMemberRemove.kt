package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberRemove(
        @SerialName("guild_id") val guildId: String,
        @SerialName("user") val user: User
)