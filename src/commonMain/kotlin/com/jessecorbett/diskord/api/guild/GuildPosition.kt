package com.jessecorbett.diskord.api.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildPosition(
    @SerialName("id") val id: String,
    @SerialName("position") val position: Int
)
