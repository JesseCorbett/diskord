package com.jessecorbett.diskord.api.gateway.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hello(
    @SerialName("heartbeat_interval") val heartbeatInterval: Long,
    @SerialName("_trace") val trace: List<String>
)
