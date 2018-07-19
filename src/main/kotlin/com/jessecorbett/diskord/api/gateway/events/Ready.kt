package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.Channel
import com.jessecorbett.diskord.api.User

data class Ready(
        @JsonProperty("v") val gatewayProtocolVersion: Int,
        @JsonProperty("user") val user: User,
        @JsonProperty("user_settings") val userSettings: Any,
        @JsonProperty("private_channels") val directMessageChannels: List<Channel>,
        @JsonProperty("guilds") val guilds: List<UnavailableGuild>,
        @JsonProperty("session_id") val sessionId: String,
        @JsonProperty("relationships") val relationships: List<Any>,
        @JsonProperty("presences") val presences: List<Any>,
        @JsonProperty("_trace") val debug: List<String>
)
