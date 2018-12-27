package com.jessecorbett.diskord.api.websocket.events

import com.fasterxml.jackson.annotation.JsonProperty

data class VoiceServerUpdate(
        @JsonProperty("token") val voiceServerToken: String,
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("endpoint") val voiceServerHost: String
)