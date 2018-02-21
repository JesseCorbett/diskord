package com.jessecorbett.diskord.api.events

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class TypingStart(
        @JsonProperty("user_id") val userId: String,
        @JsonProperty("timestamp") val timestamp: Instant,
        @JsonProperty("channel_id") val channelId: String
)
