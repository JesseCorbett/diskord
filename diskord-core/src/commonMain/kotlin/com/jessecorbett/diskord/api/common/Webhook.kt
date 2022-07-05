package com.jessecorbett.diskord.api.common

import com.jessecorbett.diskord.internal.CodeEnum
import com.jessecorbett.diskord.internal.CodeEnumSerializer
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

@Serializable(with = WebhookTypeSerializer::class)
public enum class WebhookType(public override val code: Int): CodeEnum {
    UNKNOWN(-1),
    /**
     * The main webhook type
     */
    INCOMING(0),

    /**
     * An internal webhook type used for the Channel Following feature
     */
    CHANNEL_FOLLOWER(1)
}

public class WebhookTypeSerializer : CodeEnumSerializer<WebhookType>(WebhookType.UNKNOWN, WebhookType.values())
