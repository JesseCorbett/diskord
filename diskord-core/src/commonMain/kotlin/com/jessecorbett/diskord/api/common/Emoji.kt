package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Emoji(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("roles") val whitelistedRoles: List<String>? = null,
    @SerialName("user") val creator: User? = null,
    @SerialName("require_colons") val requiresWrappingColons: Boolean? = null,
    @SerialName("managed") val isManaged: Boolean? = null,
    @SerialName("animated") val isAnimated: Boolean = false,
    @SerialName("available") val isAvailable: Boolean = false
)

@Serializable
public data class PartialEmoji(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("animated") val isAnimated: Boolean = false
)

public val Emoji.stringified: String
    get() = if (id == null) {
        name ?: "null"
    } else {
        "$name:$id"
    }

/**
 * Formatted version of an emoji for use in a [Message].
 */
public val Emoji.formatted: String
    get() = when {
        id == null -> stringified
        isAnimated -> "<a:$stringified>"
        else -> "<:$stringified>"
    }
