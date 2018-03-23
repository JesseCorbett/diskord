package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.*
import com.jessecorbett.diskord.api.models.Attachment
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
        @JsonProperty("mentions") val usersMentioned: Array<User> = emptyArray(),
        @JsonProperty("mention_roles") val rolesIdsMentioned: Array<String> = emptyArray(),
        @JsonProperty("attachments") val attachments: Array<Attachment> = emptyArray(),
        @JsonProperty("embeds") val embeds: Array<Embed> = emptyArray(),
        @JsonProperty("reactions") val reactions: Array<Reaction> = emptyArray(),
        @JsonProperty("nonce") val validationNonce: String?,
        @JsonProperty("pinned") val isPinned: Boolean?,
        @JsonProperty("webhook_id") val webHookId: String?,
        @JsonProperty("type") val type: MessageType?,
        @JsonProperty("activity") val activity: MessageActivity?,
        @JsonProperty("application") val application: MessageApplication?
)
