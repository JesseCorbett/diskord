package com.jessecorbett.diskord.api

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class Embed(
        @JsonProperty("title") val title: String?,
        @JsonProperty("type") val type: String,
        @JsonProperty("description") val description: String?,
        @JsonProperty("url") val url: String?,
        @JsonProperty("timestamp") val timestamp: Instant?,
        @JsonProperty("color") val color: Int,
        @JsonProperty("footer") val footer: EmbedFooter?,
        @JsonProperty("image") val image: EmbedImage?,
        @JsonProperty("thumbnail") val thumbnail: EmbedImage?,
        @JsonProperty("video") val video: EmbedVideo?,
        @JsonProperty("provider") val provider: EmbedProvider?,
        @JsonProperty("author") val author: EmbedAuthor?,
        @JsonProperty("fields") val fields: Array<EmbedField> = emptyArray()
)

data class EmbedImage(
        @JsonProperty("url") val url: String,
        @JsonProperty("proxy_url") val proxyUrl: String,
        @JsonProperty("height") val imageHeight: Int,
        @JsonProperty("width") val imageWidth: Int
)

data class EmbedVideo(
        @JsonProperty("url") val url: String,
        @JsonProperty("height") val videoHeight: Int,
        @JsonProperty("width") val videoWidth: Int
)

data class EmbedProvider(
        @JsonProperty("name") val name: String,
        @JsonProperty("url") val url: String?
)

data class EmbedAuthor(
        @JsonProperty("name") val name: String,
        @JsonProperty("url") val authorUrl: String?,
        @JsonProperty("icon_url") val authorImageUrl: String?,
        @JsonProperty("proxy_icon_url") val authorImageProxyUrl: String?
)

data class EmbedFooter(
        @JsonProperty("text") val text: String,
        @JsonProperty("icon_url") val iconUrl: String?,
        @JsonProperty("proxy_icon_url") val iconProxyUrl: String?
)

data class EmbedField(
        @JsonProperty("name") val name: String,
        @JsonProperty("value") val value: String,
        @JsonProperty("inline") val inline: Boolean
)
