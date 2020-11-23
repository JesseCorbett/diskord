package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.common.Channel
import com.jessecorbett.diskord.api.common.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ready(
    @SerialName("v") val gatewayProtocolVersion: Int,
    @SerialName("user") val user: User,
    @SerialName("private_channels") val directMessageChannels: List<Channel>, // Always empty right now, see createDM API call
    @SerialName("guilds") val guilds: List<UnavailableGuild>,
    @SerialName("session_id") val sessionId: String,
    @SerialName("_trace") val debug: List<String>,
    @SerialName("shard") val shardIdAndNumber: List<Int>? = null
)
