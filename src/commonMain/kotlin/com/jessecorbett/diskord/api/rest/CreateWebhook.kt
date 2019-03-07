package com.jessecorbett.diskord.api.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateWebhook(
    @SerialName("name") val name: String,
    @SerialName("avatar") val base64AvatarData: String? = null
)
