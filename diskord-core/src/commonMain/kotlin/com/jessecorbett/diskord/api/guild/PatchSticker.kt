package com.jessecorbett.diskord.api.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PatchSticker(
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("tags") val tags: String? = null,
)
