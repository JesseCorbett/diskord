package com.jessecorbett.diskord.api.rest.webhook

import com.jessecorbett.diskord.api.common.Embed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebhookSubmission(
    @SerialName("content") val content: String?,
    @SerialName("username") val overrideUsername: String? = null,
    @SerialName("avatar_url") val overrideAvatarUrl: String? = null,
    @SerialName("tts") val tts: Boolean = false,
    @SerialName("file") val fileContent: List<Byte>? = null,
    @SerialName("embeds") val embeds: List<Embed> = mutableListOf(),
    @SerialName("payload_json") val fileUploadEmbed: String? = null
)
