package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserConnection(
        @SerialName("id") val id: String,
        @SerialName("name") val name: String,
        @SerialName("type") val type: String,
        @SerialName("revoked") val revoked: Boolean,
        @SerialName("integrations") val integrations: List<GuildIntegration> = emptyList()
)
