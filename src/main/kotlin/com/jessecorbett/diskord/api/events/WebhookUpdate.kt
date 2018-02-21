package com.jessecorbett.diskord.api.events

import com.fasterxml.jackson.annotation.JsonProperty

data class WebhookUpdate(
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("channel_id") val channelId: String
)