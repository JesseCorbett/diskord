package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
    @SerialName("id") val id: String?,
    @SerialName("name") val name: String,
    @SerialName("roles") val whitelistedRoles: List<String>? = null,
    @SerialName("user") val creator: User? = null,
    @SerialName("require_colons") val requiresWrappingColons: Boolean? = null,
    @SerialName("managed") val isManaged: Boolean? = null,
    @SerialName("animated") val isAnimated: Boolean = false
)

val Emoji.stringified: String
    get() = if (id == null) {
        name
    } else {
        "$name:$id"
    }

/**
 * Formatted version of an emoji for use in a [Message].
 */
val Emoji.formatted: String
    get() = when {
        id == null -> stringified
        isAnimated -> "<a:$stringified>"
        else -> "<:$stringified>"
    }
