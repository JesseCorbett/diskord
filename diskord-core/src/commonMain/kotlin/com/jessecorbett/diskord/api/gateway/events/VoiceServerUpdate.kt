package com.jessecorbett.diskord.api.gateway.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class VoiceServerUpdate(
    @SerialName("token") val voiceServerToken: String,
    @SerialName("guild_id") val guildId: String,
    @SerialName("endpoint") val voiceServerHost: String
)
