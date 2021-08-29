package com.jessecorbett.diskord.api.webhook

import com.jessecorbett.diskord.api.channel.AllowedMentions
import com.jessecorbett.diskord.api.common.Attachment
import com.jessecorbett.diskord.api.common.Embed
import com.jessecorbett.diskord.api.common.MessageComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PatchWebhookMessage(
    @SerialName("content") val content: String?,
    @SerialName("embeds") val embeds: List<Embed> = emptyList(),
    @SerialName("file") val fileContent: List<Byte>? = null,
    @SerialName("payload_json") val fileUploadEmbed: String? = null,
    @SerialName("allowed_mentions") val allowedMentions: AllowedMentions = AllowedMentions.ALL,
    @SerialName("attachments") val attachments: List<Attachment> = emptyList(),
    @SerialName("components") val components: List<MessageComponent> = emptyList(),
)
