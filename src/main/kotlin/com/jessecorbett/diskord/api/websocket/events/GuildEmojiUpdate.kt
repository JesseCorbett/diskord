package com.jessecorbett.diskord.api.websocket.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.model.Emoji

data class GuildEmojiUpdate(
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("emojis") val emojis: List<Emoji>
)
