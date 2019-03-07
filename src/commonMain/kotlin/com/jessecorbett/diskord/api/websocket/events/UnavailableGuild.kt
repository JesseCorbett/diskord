package com.jessecorbett.diskord.api.websocket.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnavailableGuild(
    @SerialName("id") val id: String,
    @SerialName("unavailable") val unavailable: Boolean
)
