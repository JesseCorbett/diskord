package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty

data class VoiceServerUpdate(
        @JsonProperty("token") val voiceServerToken: String,
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("endpoint") val voiceServerHost: String
)