package com.jessecorbett.diskord.api.rest

import com.jessecorbett.diskord.api.model.Embed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebhookSubmission(
        @SerialName("content") val content: String? = null,
        @SerialName("username") val overrideUsername: String? = null,
        @SerialName("avatar_url") val overrideAvatarUrl: String? = null,
        @SerialName("tts") val tts: Boolean = false,
        @SerialName("file") val fileContent: List<Byte>? = null,
        @SerialName("embed") val embed: Embed? = null,
        @SerialName("payload_json") val fileUploadEmbed: String? = null
)
