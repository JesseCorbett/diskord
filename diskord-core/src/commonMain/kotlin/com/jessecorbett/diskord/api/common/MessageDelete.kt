package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MessageDelete(
    @SerialName("id") val id: String,
    @SerialName("channel_id") val channelId: String,
    @SerialName("guild_id") val guildId: String? = null
)
