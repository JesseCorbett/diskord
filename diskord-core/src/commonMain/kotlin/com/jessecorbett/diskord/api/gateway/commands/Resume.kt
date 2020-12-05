package com.jessecorbett.diskord.api.gateway.commands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Resume(
    @SerialName("token") val token: String,
    @SerialName("session_id") val sessionId: String,
    @SerialName("seq") val sequenceNumber: Int
)
