package com.jessecorbett.diskord.api.common

import kotlinx.serialization.*

@Serializable
data class Guild(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon") val iconHash: String?,
    @SerialName("splash") val splashHash: String?,
    @SerialName("owner") val userIsOwner: Boolean? = null,
    @SerialName("owner_id") val ownerId: String,
    @SerialName("permissions") val permissions: Permissions? = null,
    @SerialName("region") val region: String,
    @SerialName("afk_channel_id") val afkChannelId: String?,
    @SerialName("afk_timeout") val afkTimeoutSeconds: Int,
    @SerialName("embed_enabled") val embedEnabled: Boolean? = null,
    @SerialName("embed_channel_id") val embeddedChannelId: String? = null,
    @SerialName("verification_level") val verificationLevel: VerificationLevel,
    @SerialName("default_message_notifications") val defaultMessageNotificationLevel: NotificationsLevel,
    @SerialName("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel,
    @SerialName("roles") val roles: List<Role>,
    @SerialName("emojis") val emojis: List<Emoji>,
    @SerialName("features") val features: List<String>,
    @SerialName("mfa_level") val mfaLevel: MFALevel,
    @SerialName("application_id") val owningApplicationId: String?,
    @SerialName("widget_enabled") val widgetEnabled: Boolean? = null,
    @SerialName("widget_channel_id") val widgetChannelId: String? = null,
    @SerialName("system_channel_id") val systemMessageChannelId: String?
)

@Serializable
enum class VerificationLevel {
    @SerialName("0") NONE,
    @SerialName("1") LOW,
    @SerialName("2") MEDIUM,
    @SerialName("3") HIGH,
    @SerialName("4") VERY_HIGH
}

@Serializable
enum class NotificationsLevel {
    @SerialName("0") ALL_MESSAGES,
    @SerialName("1") ONLY_MENTIONS
}

@Serializable
enum class ExplicitContentFilterLevel {
    @SerialName("0") DISABLED,
    @SerialName("1") MEMBERS_WITHOUT_ROLES,
    @SerialName("2") ALL_MEMBERS
}

@Serializable
enum class MFALevel {
    @SerialName("0") NONE,
    @SerialName("1") ELEVATED
}
