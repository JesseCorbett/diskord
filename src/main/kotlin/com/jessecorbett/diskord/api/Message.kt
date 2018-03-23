package com.jessecorbett.diskord.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.models.Attachment
import java.time.Instant

data class Message(
        @JsonProperty("id") val id: String,
        @JsonProperty("channel_id") val channelId: String,
        @JsonProperty("author") val author: User?,
        @JsonProperty("content") val content: String = "",
        @JsonProperty("timestamp") val sentAt: Instant = Instant.now(),
        @JsonProperty("edited_timestamp") val editedAt: Instant?,
        @JsonProperty("tts") val isTTS: Boolean,
        @JsonProperty("mention_everyone") val mentionsEveryone: Boolean,
        @JsonProperty("mentions") val usersMentioned: Array<User> = emptyArray(),
        @JsonProperty("mention_roles") val rolesIdsMentioned: Array<String> = emptyArray(),
        @JsonProperty("attachments") val attachments: Array<Attachment> = emptyArray(),
        @JsonProperty("embeds") val embeds: Array<Embed> = emptyArray(),
        @JsonProperty("reactions") val reactions: Array<Reaction> = emptyArray(),
        @JsonProperty("nonce") val validationNonce: String?,
        @JsonProperty("pinned") val isPinned: Boolean,
        @JsonProperty("webhook_id") val webHookId: String?,
        @JsonProperty("type") val type: MessageType,
        @JsonProperty("activity") val activity: MessageActivity?,
        @JsonProperty("application") val application: MessageApplication?
)

enum class MessageType(val code: Int) {
    DEFAULT(0),
    RECIPIENT_ADD(1),
    RECIPIENT_REMOVE(2),
    CALL(3),
    CHANNEL_NAME_CHANGE(4),
    CHANNEL_ICON_CHANGE(5),
    CHANNEL_PINNED_MESSAGE(6),
    GUILD_MEMBER_JOIN(7)
}

data class MessageActivity(
        @JsonProperty("type") val type: MessageActivityType,
        @JsonProperty("party_id") val partyId: String
)

enum class MessageActivityType(val code: Int) {
    JOIN(0),
    SPECTATE(1),
    LISTEN(2),
    JOIN_REQUEST(3)
}

data class MessageApplication(
        @JsonProperty("id") val id: String,
        @JsonProperty("cover_image") val coverImage: String,
        @JsonProperty("description") val description: String,
        @JsonProperty("icon") val icon: String,
        @JsonProperty("name") val name: String
)
