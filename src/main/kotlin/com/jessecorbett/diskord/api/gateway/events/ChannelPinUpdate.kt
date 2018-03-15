package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class ChannelPinUpdate(
        @JsonProperty("channel_id") val channelId: String,
        @JsonProperty("last_pin_timestamp") val lastPinAt: Instant?
)
