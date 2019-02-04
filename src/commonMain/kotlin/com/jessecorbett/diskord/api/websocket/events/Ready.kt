package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.model.Channel
import com.jessecorbett.diskord.api.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ready(
        @SerialName("v") val gatewayProtocolVersion: Int,
        @SerialName("user") val user: User,
        @SerialName("user_settings") val userSettings: Any,
        @SerialName("private_channels") val directMessageChannels: List<Channel>,
        @SerialName("guilds") val guilds: List<UnavailableGuild>,
        @SerialName("session_id") val sessionId: String,
        @SerialName("relationships") val relationships: List<Any>,
        @SerialName("presences") val presences: List<Any>,
        @SerialName("_trace") val debug: List<String>
)
