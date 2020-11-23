package com.jessecorbett.diskord.api.rest.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateGuildIntegration(
    @SerialName("type") val type: String,
    @SerialName("id") val id: String
)
