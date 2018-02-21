package com.jessecorbett.diskord.api.gateway.commands

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateVoiceState(
        @JsonProperty("guild_id") val snowflakeId: String,
        @JsonProperty("channel_id") val channelId: String,
        @JsonProperty("limit") val maxResults: Int = 0,
        @JsonProperty("self_mute") val userIsMuted: Boolean,
        @JsonProperty("self_deaf") val userIsDeafened: Boolean
)
