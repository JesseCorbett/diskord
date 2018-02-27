package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class GuildEmbed(
        @JsonProperty("enabled") val enabled: Boolean,
        @JsonProperty("channel_id") val channelId: String
)
