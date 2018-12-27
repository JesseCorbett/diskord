package com.jessecorbett.diskord.api.websocket.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.model.*
import java.time.Instant

data class MessageUpdate(
        @JsonProperty("id") val id: String,
        @JsonProperty("channel_id") val channelId: String,
        @JsonProperty("author") val author: User?,
        @JsonProperty("content") val content: String?,
        @JsonProperty("timestamp") val sentAt: Instant?,
        @JsonProperty("edited_timestamp") val editedAt: Instant?,
        @JsonProperty("tts") val isTTS: Boolean?,
        @JsonProperty("mention_everyone") val mentionsEveryone: Boolean?,
        @JsonProperty("mentions") val usersMentioned: List<User> = emptyList(),
        @JsonProperty("mention_roles") val rolesIdsMentioned: List<String> = emptyList(),
        @JsonProperty("attachments") val attachments: List<Attachment> = emptyList(),
        @JsonProperty("embeds") val embeds: List<Embed> = emptyList(),
        @JsonProperty("reactions") val reactions: List<Reaction> = emptyList(),
        @JsonProperty("nonce") val validationNonce: String?,
        @JsonProperty("pinned") val isPinned: Boolean?,
        @JsonProperty("webhook_id") val webHookId: String?,
        @JsonProperty("type") val type: MessageType?,
        @JsonProperty("activity") val activity: MessageActivity?,
        @JsonProperty("application") val application: MessageApplication?
)
