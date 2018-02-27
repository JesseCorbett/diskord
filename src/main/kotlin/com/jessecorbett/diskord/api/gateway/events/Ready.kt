package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.models.Channel
import com.jessecorbett.diskord.api.models.User

data class Ready(
        @JsonProperty("v") val gatewayProtocolVersion: Int,
        @JsonProperty("user") val user: User,
        @JsonProperty("user_settings") val userSettings: Any,
        @JsonProperty("private_channels") val directMessageChannels: Array<Channel>,
        @JsonProperty("guilds") val guilds: Array<UnavailableGuild>,
        @JsonProperty("session_id") val sessionId: String,
        @JsonProperty("relationships") val relationships: Array<Any>,
        @JsonProperty("presences") val presences: Array<Any>,
        @JsonProperty("_trace") val debug: Array<String>
)
