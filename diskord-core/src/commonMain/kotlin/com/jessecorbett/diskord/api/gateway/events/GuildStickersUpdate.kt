package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.Sticker
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildStickersUpdate(
    @SerialName("guild_id") val guildId: String,
    @SerialName("stickers") val emojis: List<Sticker>
)
