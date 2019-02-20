package com.jessecorbett.diskord.api.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateEmoji(
    @SerialName("name") val name: String,
    @SerialName("image") val base64imageData: String,
    @SerialName("roles") val roleIds: List<String>
)
