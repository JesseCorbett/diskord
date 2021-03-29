package com.jessecorbett.diskord.api.gateway.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class UnavailableGuild(
    @SerialName("id") val guildId: String,
    @SerialName("unavailable") val unavailable: Boolean
)
