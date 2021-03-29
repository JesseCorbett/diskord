package com.jessecorbett.diskord.api.gateway.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ChannelPinUpdate(
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("channel_id") val channelId: String,
    @SerialName("last_pin_timestamp") val lastPinAt: String? = null
)
