package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.model.Channel
import com.jessecorbett.diskord.api.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Ready(
        @SerialName("v") val gatewayProtocolVersion: Int,
        @SerialName("user") val user: User,
        @SerialName("user_settings") val userSettings: JsonElement,
        @SerialName("private_channels") val directMessageChannels: List<Channel>,
        @SerialName("guilds") val guilds: List<UnavailableGuild>,
        @SerialName("session_id") val sessionId: String,
        @SerialName("relationships") val relationships: List<JsonElement>,
        @SerialName("presences") val presences: List<JsonElement>,
        @SerialName("_trace") val debug: List<String>
)
