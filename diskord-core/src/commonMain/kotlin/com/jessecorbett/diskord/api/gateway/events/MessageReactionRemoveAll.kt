package com.jessecorbett.diskord.api.gateway.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MessageReactionRemoveAll(
    @SerialName("channel_id") val channelId: String,
    @SerialName("message_id") val messageId: String,
    @SerialName("guild_id") val guildId: String? = null
)
