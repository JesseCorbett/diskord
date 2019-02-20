package com.jessecorbett.diskord.api.websocket.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebhookUpdate(
    @SerialName("guild_id") val guildId: String,
    @SerialName("channel_id") val channelId: String
)
