package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.Emoji
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildEmojiUpdate(
    @SerialName("guild_id") val guildId: String,
    @SerialName("emojis") val emojis: List<Emoji>
)
