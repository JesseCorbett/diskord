package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class CreateMessage(
        @JsonProperty("content") val content: String,
        @JsonProperty("nonce") val nonce: String? = null,
        @JsonProperty("tts") val tts: Boolean = false,
        @JsonProperty("file") val fileContent: ByteArray? = null,
        @JsonProperty("embed") val embed: Embed? = null,
        @JsonProperty("payload_json") val fileUploadEmbed: String? = null
)

data class Embed(
        @JsonProperty("title") var title: String? = null,
        @JsonProperty("description") var description: String? = null,
        @JsonProperty("url") var url: String? = null,
        @JsonProperty("timestamp") var timestamp: Instant? = null,
        @JsonProperty("color") var color: Int? = null,
        @JsonProperty("footer") var footer: EmbedFooter? = null,
        @JsonProperty("image") var image: EmbedImage? = null,
        @JsonProperty("thumbnail") var thumbnail: EmbedImage? = null,
        @JsonProperty("video") var video: EmbedVideo? = null,
        @JsonProperty("provider") var provider: EmbedProvider? = null,
        @JsonProperty("author") var author: EmbedAuthor? = null,
        @JsonProperty("fields") var fields: MutableList<EmbedField> = ArrayList(),
        @JsonProperty("type") var type: String = "rich"
)

data class EmbedImage(
        @JsonProperty("url") val url: String,
        @JsonProperty("proxy_url") var proxyUrl: String? = null,
        @JsonProperty("height") var imageHeight: Int = 0,
        @JsonProperty("width") var imageWidth: Int = 0
)

data class EmbedVideo(
        @JsonProperty("url") val url: String,
        @JsonProperty("height") var videoHeight: Int = 0,
        @JsonProperty("width") var videoWidth: Int = 0
)

data class EmbedProvider(
        @JsonProperty("name") val name: String,
        @JsonProperty("url") val url: String?
)

data class EmbedAuthor(
        @JsonProperty("name") val name: String,
        @JsonProperty("url") var authorUrl: String? = null,
        @JsonProperty("icon_url") var authorImageUrl: String? = null,
        @JsonProperty("proxy_icon_url") var authorImageProxyUrl: String? = null
)

data class EmbedFooter(
        @JsonProperty("text") val text: String,
        @JsonProperty("icon_url") var iconUrl: String? = null,
        @JsonProperty("proxy_icon_url") var iconProxyUrl: String? = null
)

data class EmbedField(
        @JsonProperty("name") val name: String,
        @JsonProperty("value") val value: String,
        @JsonProperty("inline") val inline: Boolean
)

fun embed(block: Embed.() -> Unit) = Embed().apply(block)

fun Embed.image(url: String, block: EmbedImage.() -> Unit) {
    image = EmbedImage(url).apply(block)
}

fun Embed.video(url: String, block: EmbedVideo.() -> Unit) {
    video = EmbedVideo(url).apply(block)
}

fun Embed.provider(name: String, url: String) {
    provider = EmbedProvider(name, url)
}

fun Embed.author(name: String, block: EmbedAuthor.() -> Unit) {
    author = EmbedAuthor(name).apply(block)
}

fun Embed.footer(text: String, block: EmbedFooter.() -> Unit) {
    footer = EmbedFooter(text).apply(block)
}

fun Embed.fields(block: MutableList<EmbedField>.() -> Unit) = fields.apply(block)

fun MutableList<EmbedField>.field(name: String, value: String, inline: Boolean) = add(EmbedField(name, value, inline))
