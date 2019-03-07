package com.jessecorbett.diskord.api.websocket.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildIntegrationUpdate(@SerialName("guild_id") val guildId: String)