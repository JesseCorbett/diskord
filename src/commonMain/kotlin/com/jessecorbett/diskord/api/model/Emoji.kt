package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
        @SerialName("id") val id: String?,
        @SerialName("name") val name: String,
        @SerialName("roles") val whitelistedRoles: List<Role>?,
        @SerialName("user") val creator: User?,
        @SerialName("require_colons") val requiresWrappingColons: Boolean?,
        @SerialName("managed") val isManaged: Boolean?,
        @SerialName("animated") val isAnimated: Boolean = false
)
