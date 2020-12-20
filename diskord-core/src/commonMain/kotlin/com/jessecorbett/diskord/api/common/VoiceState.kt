package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class VoiceState(
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("channel_id") val channelId: String?,
    @SerialName("user_id") val userId: String,
    @SerialName("member") val guildMember: GuildMember,
    @SerialName("session_id") val sessionId: String,
    @SerialName("deaf") val userIsDeaf: Boolean,
    @SerialName("mute") val userIsMute: Boolean,
    @SerialName("self_mute") val userSelfIsMuted: Boolean,
    @SerialName("self_deaf") val userIsSelfDeafened: Boolean,
    @SerialName("self_stream") val userIsStreaming: Boolean? = null,
    @SerialName("self_video") val userHasCameraActive: Boolean,
    @SerialName("suppress") val userIsMutedByCurrentUser: Boolean
)
