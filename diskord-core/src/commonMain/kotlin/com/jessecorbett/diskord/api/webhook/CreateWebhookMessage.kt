package com.jessecorbett.diskord.api.webhook

import com.jessecorbett.diskord.api.channel.AllowedMentions
import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.api.common.MessageComponent
import com.jessecorbett.diskord.api.interaction.callback.InteractionCommandCallbackDataFlags
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Deprecated("Use CreateWebhookMessage instead.", ReplaceWith("CreateWebhookMessage"))
@Serializable
public data class WebhookSubmission(
    @SerialName("content") val content: String?,
    @SerialName("username") val overrideUsername: String? = null,
    @SerialName("avatar_url") val overrideAvatarUrl: String? = null,
    @SerialName("tts") val useTTS: Boolean = false,
    @SerialName("file") val fileContent: List<Byte>? = null,
    @SerialName("embeds") val embeds: List<Embed> = emptyList(),
    @SerialName("payload_json") val fileUploadEmbed: String? = null,
    @SerialName("allowed_mentions") val allowedMentions: AllowedMentions = AllowedMentions.ALL
)

@Serializable
public data class CreateWebhookMessage(
    @SerialName("content") val content: String?,
    @SerialName("username") val overrideUsername: String? = null,
    @SerialName("avatar_url") val overrideAvatarUrl: String? = null,
    @SerialName("tts") val useTTS: Boolean = false,
    @SerialName("file") val fileContent: List<Byte>? = null,
    @SerialName("embeds") val embeds: List<Embed> = emptyList(),
    @SerialName("payload_json") val fileUploadEmbed: String? = null,
    @SerialName("allowed_mentions") val allowedMentions: AllowedMentions = AllowedMentions.ALL,
    @SerialName("components") val components: List<MessageComponent> = emptyList(),
    @SerialName("flags") val flags: InteractionCommandCallbackDataFlags = InteractionCommandCallbackDataFlags.NONE
)
