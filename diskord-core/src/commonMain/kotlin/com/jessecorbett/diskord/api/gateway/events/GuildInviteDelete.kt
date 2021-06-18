package com.jessecorbett.diskord.api.gateway.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildInviteDelete(
    @SerialName("channel_id") val channelId: String,
    @SerialName("code") val code: String,
    @SerialName("guild_id") val guildId: String? = null
)
