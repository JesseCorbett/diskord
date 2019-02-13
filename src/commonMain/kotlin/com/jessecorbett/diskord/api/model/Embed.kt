package com.jessecorbett.diskord.api.model

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Embed(
        @Optional @SerialName("title") val title: String? = null,
        @Optional @SerialName("description") val description: String? = null,
        @Optional @SerialName("url") val url: String? = null,
        @Optional @SerialName("timestamp") val timestamp: String? = null,
        @Optional @SerialName("color") val color: Color? = null,
        @Optional @SerialName("footer") val footer: EmbedFooter? = null,
        @Optional @SerialName("image") val image: EmbedImage? = null,
        @Optional @SerialName("thumbnail") val thumbnail: EmbedImage? = null,
        @Optional @SerialName("video") val video: EmbedVideo? = null,
        @Optional @SerialName("provider") val provider: EmbedProvider? = null,
        @Optional @SerialName("author") val author: EmbedAuthor? = null,
        @Optional @SerialName("fields") val fields: List<EmbedField> = emptyList(),
        @SerialName("type") val type: String = "rich"
)

@Serializable
data class EmbedImage(
        @SerialName("url") val url: String,
        @Optional @SerialName("proxy_url") val proxyUrl: String? = null,
        @SerialName("height") val imageHeight: Int = 0,
        @SerialName("width") val imageWidth: Int = 0
)

@Serializable
data class EmbedVideo(
        @SerialName("url") val url: String,
        @SerialName("height") val videoHeight: Int = 0,
        @SerialName("width") val videoWidth: Int = 0
)

@Serializable
data class EmbedProvider(
        @SerialName("name") val name: String,
        @SerialName("url") val url: String?
)

@Serializable
data class EmbedAuthor(
        @SerialName("name") val name: String,
        @Optional @SerialName("url") val authorUrl: String? = null,
        @Optional @SerialName("icon_url") val authorImageUrl: String? = null,
        @Optional @SerialName("proxy_icon_url") val authorImageProxyUrl: String? = null
)

@Serializable
data class EmbedFooter(
        @SerialName("text") val text: String,
        @Optional @SerialName("icon_url") val iconUrl: String? = null,
        @Optional @SerialName("proxy_icon_url") val iconProxyUrl: String? = null
)

@Serializable
data class EmbedField(
        @SerialName("name") val name: String,
        @SerialName("value") val value: String,
        @SerialName("inline") val inline: Boolean
)
