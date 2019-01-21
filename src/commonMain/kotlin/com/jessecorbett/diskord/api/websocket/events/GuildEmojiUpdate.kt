package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.model.Emoji
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildEmojiUpdate(
        @SerialName("guild_id") val guildId: String,
        @SerialName("emojis") val emojis: List<Emoji>
)
