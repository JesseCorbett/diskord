package com.jessecorbett.diskord.api.model

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
    @SerialName("id") val id: String?,
    @SerialName("name") val name: String,
    @Optional @SerialName("roles") val whitelistedRoles: List<String>? = null,
    @Optional @SerialName("user") val creator: User? = null,
    @Optional @SerialName("require_colons") val requiresWrappingColons: Boolean? = null,
    @Optional @SerialName("managed") val isManaged: Boolean? = null,
    @Optional @SerialName("animated") val isAnimated: Boolean = false
)

val Emoji.stringified: String
    get() = if (id == null) {
        name
    } else {
        "$id:$name"
    }
