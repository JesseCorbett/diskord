package com.jessecorbett.diskord.api.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PatchEmoji(
    @SerialName("name") val name: String,
    @SerialName("roles") val roles: List<String>
)
