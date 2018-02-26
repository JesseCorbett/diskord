package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.models.Emoji

data class GuildEmojiUpdate(
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("emojis") val emojis: Array<Emoji>
)
