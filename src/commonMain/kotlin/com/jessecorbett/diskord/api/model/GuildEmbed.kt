package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildEmbed(
        @SerialName("enabled") val enabled: Boolean,
        @SerialName("channel_id") val channelId: String
)
