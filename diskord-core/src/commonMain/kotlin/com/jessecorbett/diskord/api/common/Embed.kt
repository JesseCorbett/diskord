package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Embed(
    @SerialName("title") val title: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("timestamp") val timestamp: String? = null,
    @SerialName("color") val color: Color? = null,
    @SerialName("footer") val footer: EmbedFooter? = null,
    @SerialName("image") val image: EmbedImage? = null,
    @SerialName("thumbnail") val thumbnail: EmbedImage? = null,
    @SerialName("video") val video: EmbedVideo? = null,
    @SerialName("provider") val provider: EmbedProvider? = null,
    @SerialName("author") val author: EmbedAuthor? = null,
    @SerialName("fields") val fields: List<EmbedField> = emptyList(),
    @SerialName("type") val type: String = "rich"
)

@Serializable
public data class EmbedImage(
    @SerialName("url") val url: String? = null,
    @SerialName("proxy_url") val proxyUrl: String? = null,
    @SerialName("height") val imageHeight: Int = 0,
    @SerialName("width") val imageWidth: Int = 0
)

@Serializable
public data class EmbedVideo(
    @SerialName("url") val url: String? = null,
    @SerialName("height") val videoHeight: Int = 0,
    @SerialName("width") val videoWidth: Int = 0
)

@Serializable
public data class EmbedProvider(
    @SerialName("name") val name: String? = null,
    @SerialName("url") val url: String? = null
)

@Serializable
public data class EmbedAuthor(
    @SerialName("name") val name: String,
    @SerialName("url") val authorUrl: String? = null,
    @SerialName("icon_url") val authorImageUrl: String? = null,
    @SerialName("proxy_icon_url") val authorImageProxyUrl: String? = null
)

@Serializable
public data class EmbedFooter(
    @SerialName("text") val text: String,
    @SerialName("icon_url") val iconUrl: String? = null,
    @SerialName("proxy_icon_url") val iconProxyUrl: String? = null
)

@Serializable
public data class EmbedField(
    @SerialName("name") val name: String,
    @SerialName("value") val value: String,
    @SerialName("inline") val inline: Boolean? = null
)
