package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.model.*
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageUpdate(
        @SerialName("id") val id: String,
        @SerialName("channel_id") val channelId: String,
        @Optional @SerialName("author") val author: User? = null,
        @Optional @SerialName("content") val content: String? = null,
        @SerialName("timestamp") val sentAt: String?,
        @SerialName("edited_timestamp") val editedAt: String?,
        @SerialName("tts") val isTTS: Boolean?,
        @SerialName("mention_everyone") val mentionsEveryone: Boolean?,
        @SerialName("mentions") val usersMentioned: List<User> = emptyList(),
        @SerialName("mention_roles") val rolesIdsMentioned: List<String> = emptyList(),
        @SerialName("attachments") val attachments: List<Attachment> = emptyList(),
        @SerialName("embeds") val embeds: List<Embed> = emptyList(),
        @SerialName("reactions") val reactions: List<Reaction> = emptyList(),
        @SerialName("nonce") val validationNonce: String?,
        @SerialName("pinned") val isPinned: Boolean?,
        @SerialName("webhook_id") val webHookId: String?,
        @SerialName("type") val type: MessageType?,
        @SerialName("activity") val activity: MessageActivity?,
        @SerialName("application") val application: MessageApplication?
)
