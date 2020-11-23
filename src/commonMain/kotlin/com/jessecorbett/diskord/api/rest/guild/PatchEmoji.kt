package com.jessecorbett.diskord.api.rest.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchEmoji(
    @SerialName("name") val name: String,
    @SerialName("roles") val roles: List<String>
)
