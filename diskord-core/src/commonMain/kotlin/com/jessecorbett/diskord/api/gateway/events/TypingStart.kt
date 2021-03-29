package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.GuildMember
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class TypingStart(
    @SerialName("user_id") val userId: String,
    @SerialName("timestamp") val timestamp: String,
    @SerialName("channel_id") val channelId: String,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("member") val typingMember: GuildMember? = null
)
