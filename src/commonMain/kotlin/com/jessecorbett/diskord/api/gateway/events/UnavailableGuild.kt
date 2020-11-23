package com.jessecorbett.diskord.api.gateway.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnavailableGuild(
    @SerialName("id") val id: String,
    @SerialName("unavailable") val unavailable: Boolean
)
