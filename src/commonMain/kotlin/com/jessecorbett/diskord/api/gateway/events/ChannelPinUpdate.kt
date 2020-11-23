package com.jessecorbett.diskord.api.gateway.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelPinUpdate(
    @SerialName("channel_id") val channelId: String,
    @SerialName("last_pin_timestamp") val lastPinAt: String? = null
)
