package com.jessecorbett.diskord.api.webhook

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PatchWebhook(
    @SerialName("name") val name: String? = null,
    @SerialName("avatar") val base64AvatarData: String? = null,
    @SerialName("channel_id") val channelId: String? = null
)
