package com.jessecorbett.diskord.api.rest

import com.jessecorbett.diskord.api.model.Color
import kotlinx.io.core.ByteReadPacket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMessage(
    @SerialName("content") val content: String,
    @SerialName("nonce") val nonce: String? = null,
    @SerialName("tts") val tts: Boolean = false,
    @SerialName("file") val fileContent: List<Byte>? = null, // Not currently fully supported https://discordapp.com/developers/docs/resources/channel#create-message
    @SerialName("embed") val embed: Embed? = null,
    @SerialName("payload_json") val fileUploadEmbed: String? = null
)

@Serializable
data class Embed(
    @SerialName("title") var title: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("timestamp") var timestamp: String? = null,
    @SerialName("color") var color: Color? = null,
    @SerialName("footer") var footer: EmbedFooter? = null,
    @SerialName("image") var image: EmbedImage? = null,
    @SerialName("thumbnail") var thumbnail: EmbedImage? = null,
    @SerialName("video") var video: EmbedVideo? = null,
    @SerialName("provider") var provider: EmbedProvider? = null,
    @SerialName("author") var author: EmbedAuthor? = null,
    @SerialName("fields") var fields: MutableList<EmbedField> = ArrayList(),
    @SerialName("type") var type: String = "rich"
)

@Serializable
data class EmbedImage(
    @SerialName("url") val url: String,
    @SerialName("proxy_url") var proxyUrl: String? = null,
    @SerialName("height") var imageHeight: Int = 0,
    @SerialName("width") var imageWidth: Int = 0
)

@Serializable
data class EmbedVideo(
    @SerialName("url") val url: String,
    @SerialName("height") var videoHeight: Int = 0,
    @SerialName("width") var videoWidth: Int = 0
)

@Serializable
data class EmbedProvider(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String?
)

@Serializable
data class EmbedAuthor(
    @SerialName("name") val name: String,
    @SerialName("url") var authorUrl: String? = null,
    @SerialName("icon_url") var authorImageUrl: String? = null,
    @SerialName("proxy_icon_url") var authorImageProxyUrl: String? = null
)

@Serializable
data class EmbedFooter(
    @SerialName("text") val text: String,
    @SerialName("icon_url") var iconUrl: String? = null,
    @SerialName("proxy_icon_url") var iconProxyUrl: String? = null
)

@Serializable
data class EmbedField(
    @SerialName("name") val name: String,
    @SerialName("value") val value: String,
    @SerialName("inline") val inline: Boolean
)

data class FileData(val packet: ByteReadPacket, val filename: String)
