package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class UserConnection(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("revoked") val revoked: Boolean,
    @SerialName("integrations") val integrations: List<GuildIntegration>
)
