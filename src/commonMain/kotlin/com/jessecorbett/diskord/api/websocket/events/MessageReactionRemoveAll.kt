package com.jessecorbett.diskord.api.websocket.events

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageReactionRemoveAll(
    @SerialName("channel_id") val channelId: String,
    @SerialName("message_id") val messageId: String,
    @Optional @SerialName("guild_id") val guildId: String? = null
)
