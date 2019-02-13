package com.jessecorbett.diskord.api.model

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
        @SerialName("id") val id: String?,
        @SerialName("name") val name: String,
        @Optional @SerialName("roles") val whitelistedRoles: List<Role>? = null,
        @Optional @SerialName("user") val creator: User? = null,
        @SerialName("require_colons") val requiresWrappingColons: Boolean?,
        @SerialName("managed") val isManaged: Boolean?,
        @SerialName("animated") val isAnimated: Boolean = false
)
