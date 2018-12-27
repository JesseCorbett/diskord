package com.jessecorbett.diskord.api.websocket.events

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageReactionRemoveAll(
        @JsonProperty("channel_id") val channelId: String,
        @JsonProperty("message_id") val messageId: String
)