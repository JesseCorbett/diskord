package com.jessecorbett.diskord.api.model

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Webhook(
    @SerialName("id") val id: String,
    @Optional @SerialName("guild_id") val guildId: String? = null,
    @SerialName("channel_id") val channelId: String,
    @Optional @SerialName("user") val user: User? = null,
    @SerialName("name") val defaultName: String?,
    @SerialName("avatar") val defaultAvatarHash: String?,
    @SerialName("token") val token: String
)
