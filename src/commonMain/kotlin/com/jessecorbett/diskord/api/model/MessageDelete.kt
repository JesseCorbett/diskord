package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDelete(
    @SerialName("id") val id: String,
    @SerialName("channel_id") val channelId: String
)
