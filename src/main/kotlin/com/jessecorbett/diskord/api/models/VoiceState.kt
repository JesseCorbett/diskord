package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class VoiceState(
        @JsonProperty("guild_id") val snowflakeId: String?,
        @JsonProperty("channel_id") val channelId: String?,
        @JsonProperty("user_id") val userId: String,
        @JsonProperty("session_id") val sessionId: String,
        @JsonProperty("deaf") val userIsDeaf: Boolean,
        @JsonProperty("mute") val userIsMute: Boolean,
        @JsonProperty("self_mute") val userSelfIsMuted: Boolean,
        @JsonProperty("self_deaf") val userIsSelfDeafened: Boolean,
        @JsonProperty("suppress") val userIsSuppressedByUs: Boolean
)
