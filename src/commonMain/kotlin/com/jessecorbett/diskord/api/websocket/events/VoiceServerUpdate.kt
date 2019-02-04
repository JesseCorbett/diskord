package com.jessecorbett.diskord.api.websocket.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoiceServerUpdate(
        @SerialName("token") val voiceServerToken: String,
        @SerialName("guild_id") val guildId: String,
        @SerialName("endpoint") val voiceServerHost: String
)