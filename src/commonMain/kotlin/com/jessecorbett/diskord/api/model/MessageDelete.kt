package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageDelete(
        @JsonProperty("id") val id: String,
        @JsonProperty("channel_id") val channelId: String
)
