package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class Embed(
        @JsonProperty("title") val title: String? = null,
        @JsonProperty("description") val description: String? = null,
        @JsonProperty("url") val url: String? = null,
        @JsonProperty("timestamp") val timestamp: Instant? = null,
        @JsonProperty("color") val color: Int? = null,
        @JsonProperty("footer") val footer: EmbedFooter? = null,
        @JsonProperty("image") val image: EmbedImage? = null,
        @JsonProperty("thumbnail") val thumbnail: EmbedImage? = null,
        @JsonProperty("video") val video: EmbedVideo? = null,
        @JsonProperty("provider") val provider: EmbedProvider? = null,
        @JsonProperty("author") val author: EmbedAuthor? = null,
        @JsonProperty("fields") val fields: List<EmbedField> = emptyList(),
        @JsonProperty("type") val type: String = "rich"
)

data class EmbedImage(
        @JsonProperty("url") val url: String,
        @JsonProperty("proxy_url") val proxyUrl: String? = null,
        @JsonProperty("height") val imageHeight: Int = 0,
        @JsonProperty("width") val imageWidth: Int = 0
)

data class EmbedVideo(
        @JsonProperty("url") val url: String,
        @JsonProperty("height") val videoHeight: Int = 0,
        @JsonProperty("width") val videoWidth: Int = 0
)

data class EmbedProvider(
        @JsonProperty("name") val name: String,
        @JsonProperty("url") val url: String?
)

data class EmbedAuthor(
        @JsonProperty("name") val name: String,
        @JsonProperty("url") val authorUrl: String? = null,
        @JsonProperty("icon_url") val authorImageUrl: String? = null,
        @JsonProperty("proxy_icon_url") val authorImageProxyUrl: String? = null
)

data class EmbedFooter(
        @JsonProperty("text") val text: String,
        @JsonProperty("icon_url") val iconUrl: String? = null,
        @JsonProperty("proxy_icon_url") val iconProxyUrl: String? = null
)

data class EmbedField(
        @JsonProperty("name") val name: String,
        @JsonProperty("value") val value: String,
        @JsonProperty("inline") val inline: Boolean
)
