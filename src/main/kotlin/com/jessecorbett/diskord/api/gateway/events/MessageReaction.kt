package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.models.Emoji

data class MessageReaction(
        @JsonProperty("user_id") val userId: String,
        @JsonProperty("channel_id") val channelId: String,
        @JsonProperty("message_id") val messageId: String,
        @JsonProperty("emoji") val emoji: Emoji
)