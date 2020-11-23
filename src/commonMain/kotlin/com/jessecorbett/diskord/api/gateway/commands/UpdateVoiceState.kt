package com.jessecorbett.diskord.api.gateway.commands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateVoiceState(
    @SerialName("guild_id") val snowflakeId: String,
    @SerialName("channel_id") val channelId: String,
    @SerialName("limit") val maxResults: Int = 0,
    @SerialName("self_mute") val userIsMuted: Boolean,
    @SerialName("self_deaf") val userIsDeafened: Boolean
)
