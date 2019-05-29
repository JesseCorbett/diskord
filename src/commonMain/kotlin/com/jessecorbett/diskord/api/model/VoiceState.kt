package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoiceState(
    @SerialName("guild_id") val snowflakeId: String? = null,
    @SerialName("channel_id") val channelId: String?,
    @SerialName("user_id") val userId: String,
    @SerialName("session_id") val sessionId: String,
    @SerialName("deaf") val userIsDeaf: Boolean,
    @SerialName("mute") val userIsMute: Boolean,
    @SerialName("self_mute") val userSelfIsMuted: Boolean,
    @SerialName("self_deaf") val userIsSelfDeafened: Boolean,
    @SerialName("suppress") val userIsSuppressedByUs: Boolean
)
