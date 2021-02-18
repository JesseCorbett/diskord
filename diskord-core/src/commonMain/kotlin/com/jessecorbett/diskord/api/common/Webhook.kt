package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Webhook(
    @SerialName("id") val id: String,
    @SerialName("type") val type: WebhookType,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("channel_id") val channelId: String,
    @SerialName("user") val user: User? = null,
    @SerialName("name") val defaultName: String?,
    @SerialName("avatar") val defaultAvatarHash: String?,
    @SerialName("token") val token: String? = null,
    @SerialName("application_id") val applicationId: String? = null
)

@Serializable
public enum class WebhookType {
    /**
     * The main webhook type
     */
    @SerialName("0") INCOMING,

    /**
     * An internal webhook type used for the Channel Following feature
     */
    @SerialName("1") CHANNEL_FOLLOWER
}
