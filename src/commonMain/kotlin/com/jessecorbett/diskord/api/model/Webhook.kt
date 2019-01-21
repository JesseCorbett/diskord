package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Webhook(
        @SerialName("id") val id: String,
        @SerialName("guild_id") val guildId: String?,
        @SerialName("channel_id") val channelId: String,
        @SerialName("user") val user: User?,
        @SerialName("name") val defaultName: String?,
        @SerialName("avatar") val defaultAvatarHash: String?,
        @SerialName("token") val token: String
)
