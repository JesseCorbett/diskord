package com.jessecorbett.diskord.api.channel

import com.jessecorbett.diskord.api.common.Color
import com.jessecorbett.diskord.api.common.MessageReference
import io.ktor.utils.io.core.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateMessage(
    @SerialName("content") val content: String,
    @SerialName("nonce") val nonce: String? = null,
    @SerialName("tts") val tts: Boolean = false,
    @SerialName("file") val fileContent: List<Byte>? = null, // Not currently fully supported https://discordapp.com/developers/docs/resources/channel#create-message
    @SerialName("embeds") val embeds: List<Embed> = emptyList(),
    @SerialName("payload_json") val fileUploadEmbed: String? = null,
    @SerialName("allowed_mentions") val allowedMentions: AllowedMentions = AllowedMentions.ALL,
    @SerialName("message_reference") val messageReference: MessageReference? = null,
)

@Serializable
public data class Embed(
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
public data class EmbedImage(
    @SerialName("url") val url: String,
    @SerialName("proxy_url") var proxyUrl: String? = null,
    @SerialName("height") var imageHeight: Int = 0,
    @SerialName("width") var imageWidth: Int = 0
)

@Serializable
public data class EmbedVideo(
    @SerialName("url") val url: String,
    @SerialName("height") var videoHeight: Int = 0,
    @SerialName("width") var videoWidth: Int = 0
)

@Serializable
public data class EmbedProvider(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String? = null
)

@Serializable
public data class EmbedAuthor(
    @SerialName("name") val name: String,
    @SerialName("url") var authorUrl: String? = null,
    @SerialName("icon_url") var authorImageUrl: String? = null,
    @SerialName("proxy_icon_url") var authorImageProxyUrl: String? = null
)

@Serializable
public data class EmbedFooter(
    @SerialName("text") val text: String,
    @SerialName("icon_url") var iconUrl: String? = null,
    @SerialName("proxy_icon_url") var iconProxyUrl: String? = null
)

@Serializable
public data class EmbedField(
    @SerialName("name") val name: String,
    @SerialName("value") val value: String,
    @SerialName("inline") val inline: Boolean
)

// TODO: This should be moved to the common package on the next breaking pass.
public data class FileData(
    val packet: ByteReadPacket,
    val filename: String,
    val contentType: String? = null
)

@Serializable
public data class AllowedMentions(
    @SerialName("parse") val allowedMentionTypes: List<MentionTypes> = emptyList(),
    @SerialName("roles") val allowedMentionRoles: List<String> = emptyList(),
    @SerialName("users") val allowedMentionUsers: List<String> = emptyList(),
    @SerialName("replied_user") val mentionRepliedUsers: Boolean = false
) {
    public companion object {
        public val ALL: AllowedMentions = AllowedMentions(MentionTypes.values().toList())
        public val ONLY_USERS: AllowedMentions = AllowedMentions(listOf(MentionTypes.users))
        public val ONLY_ROLES: AllowedMentions = AllowedMentions(listOf(MentionTypes.roles))
        public val USERS_AND_ROLES: AllowedMentions = AllowedMentions(listOf(MentionTypes.roles, MentionTypes.users))
        public val NONE: AllowedMentions = AllowedMentions(emptyList())

        public fun forUsers(vararg userIds: String): AllowedMentions {
            return AllowedMentions(allowedMentionUsers = userIds.toList())
        }

        public fun forRoles(vararg roleIds: String): AllowedMentions {
            return AllowedMentions(allowedMentionRoles = roleIds.toList())
        }
    }
}

public enum class MentionTypes {
    roles,
    users,
    everyone
}
