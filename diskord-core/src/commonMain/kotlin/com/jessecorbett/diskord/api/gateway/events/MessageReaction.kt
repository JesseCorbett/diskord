package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.Emoji
import com.jessecorbett.diskord.api.common.GuildMember
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MessageReactionAdd(
    @SerialName("user_id") val userId: String,
    @SerialName("channel_id") val channelId: String,
    @SerialName("message_id") val messageId: String,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("emoji") val emoji: Emoji,
    @SerialName("member") val reactingMember: GuildMember? = null
)

@Serializable
public data class MessageReactionRemove(
    @SerialName("user_id") val userId: String,
    @SerialName("channel_id") val channelId: String,
    @SerialName("message_id") val messageId: String,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("emoji") val emoji: Emoji
)
