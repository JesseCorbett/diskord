package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Webhook(
    @SerialName("id") val id: String,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("channel_id") val channelId: String,
    @SerialName("user") val user: User? = null,
    @SerialName("name") val defaultName: String?,
    @SerialName("avatar") val defaultAvatarHash: String?,
    @SerialName("token") val token: String
)
