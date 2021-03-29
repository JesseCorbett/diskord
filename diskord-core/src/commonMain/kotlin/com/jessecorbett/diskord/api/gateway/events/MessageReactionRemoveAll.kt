package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.Emoji
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MessageReactionRemoveAll(
    @SerialName("channel_id") val channelId: String,
    @SerialName("message_id") val messageId: String,
    @SerialName("guild_id") val guildId: String? = null
)

@Serializable
public data class MessageReactionRemoveEmoji(
    @SerialName("channel_id") val channelId: String,
    @SerialName("message_id") val messageId: String,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("emoji") val emoji: Emoji
)
