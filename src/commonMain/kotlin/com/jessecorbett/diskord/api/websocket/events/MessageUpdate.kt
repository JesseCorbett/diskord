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
    @Optional @SerialName("timestamp") val sentAt: String? = null,
    @Optional @SerialName("edited_timestamp") val editedAt: String? = null,
    @Optional @SerialName("tts") val isTTS: Boolean? = null,
    @Optional @SerialName("mention_everyone") val mentionsEveryone: Boolean? = null,
    @Optional @SerialName("mentions") val usersMentioned: List<User> = emptyList(),
    @Optional @SerialName("mention_roles") val rolesIdsMentioned: List<String> = emptyList(),
    @Optional @SerialName("attachments") val attachments: List<Attachment> = emptyList(),
    @Optional @SerialName("embeds") val embeds: List<Embed> = emptyList(),
    @Optional @SerialName("reactions") val reactions: List<Reaction> = emptyList(),
    @Optional @SerialName("nonce") val validationNonce: String? = null,
    @Optional @SerialName("pinned") val isPinned: Boolean? = null,
    @Optional @SerialName("webhook_id") val webHookId: String? = null,
    @Optional @SerialName("type") val type: MessageType? = null,
    @Optional @SerialName("activity") val activity: MessageActivity? = null,
    @Optional @SerialName("application") val application: MessageApplication? = null
)
