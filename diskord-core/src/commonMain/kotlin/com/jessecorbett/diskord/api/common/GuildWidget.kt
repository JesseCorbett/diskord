package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildWidget(
    @SerialName("enabled") val enabled: Boolean,
    @SerialName("channel_id") val channelId: String
)
