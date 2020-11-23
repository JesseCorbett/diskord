package com.jessecorbett.diskord.api.channel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateWebhook(
    @SerialName("name") val name: String,
    @SerialName("avatar") val base64AvatarData: String? = null
)
