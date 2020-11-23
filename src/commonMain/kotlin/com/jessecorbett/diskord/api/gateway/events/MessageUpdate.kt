package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageUpdate(
    @SerialName("id") val id: String,
    @SerialName("channel_id") val channelId: String,
    @SerialName("author") val author: User? = null,
    @SerialName("content") val content: String? = null,
    @SerialName("timestamp") val sentAt: String? = null,
    @SerialName("edited_timestamp") val editedAt: String? = null,
    @SerialName("tts") val isTTS: Boolean? = null,
    @SerialName("mention_everyone") val mentionsEveryone: Boolean? = null,
    @SerialName("mentions") val usersMentioned: List<User> = emptyList(),
    @SerialName("mention_roles") val rolesIdsMentioned: List<String> = emptyList(),
    @SerialName("attachments") val attachments: List<Attachment> = emptyList(),
    @SerialName("embeds") val embeds: List<Embed> = emptyList(),
    @SerialName("reactions") val reactions: List<Reaction> = emptyList(),
    @SerialName("nonce") val validationNonce: String? = null,
    @SerialName("pinned") val isPinned: Boolean? = null,
    @SerialName("webhook_id") val webHookId: String? = null,
    @SerialName("type") val type: MessageType? = null,
    @SerialName("activity") val activity: MessageActivity? = null,
    @SerialName("application") val application: MessageApplication? = null
)
