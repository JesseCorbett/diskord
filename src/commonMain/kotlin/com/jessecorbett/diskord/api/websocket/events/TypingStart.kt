package com.jessecorbett.diskord.api.websocket.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypingStart(
        @SerialName("user_id") val userId: String,
        @SerialName("timestamp") val timestamp: String,
        @SerialName("channel_id") val channelId: String
)
