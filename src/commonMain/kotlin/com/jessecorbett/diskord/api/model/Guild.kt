package com.jessecorbett.diskord.api.model

import kotlinx.serialization.*

@Serializable
data class Guild(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon") val iconHash: String?,
    @SerialName("splash") val splashHash: String?,
    @SerialName("owner") val userIsOwner: Boolean?,
    @SerialName("owner_id") val ownerId: String,
    @SerialName("permissions") val permissions: Int?,
    @SerialName("region") val region: String,
    @SerialName("afk_channel_id") val afkChannelId: String?,
    @SerialName("afk_timeout") val afkTimeoutSeconds: Int,
    @SerialName("embed_enabled") val embedEnabled: Boolean?,
    @SerialName("embed_channel_id") val embeddedChannelId: String?,
    @SerialName("verification_level") val verificationLevel: VerificationLevel,
    @SerialName("default_message_notifications") val defaultMessageNotificationLevel: NotificationsLevel,
    @SerialName("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel,
    @SerialName("roles") val roles: List<Role>,
    @SerialName("emojis") val emojis: List<Emoji>,
    @SerialName("features") val features: List<String>,
    @SerialName("mfa_level") val mfaLevel: MFALevel,
    @SerialName("application_id") val owningApplicationId: String?,
    @SerialName("widget_enabled") val widgetEnabled: Boolean?,
    @SerialName("widget_channel_id") val widgetChannelId: String?,
    @SerialName("system_channel_id") val systemMessageChannelId: String?
)

enum class VerificationLevel(val level: Int) {
    NONE(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4)
}

enum class NotificationsLevel(val level: Int) {
    ALL_MESSAGES(0),
    ONLY_MENTIONS(1)
}

enum class ExplicitContentFilterLevel(val level: Int) {
    DISABLED(0),
    MEMBERS_WITHOUT_ROLES(1),
    ALL_MEMBERS(2)
}

enum class MFALevel(val level: Int) {
    NONE(0),
    ELEVATED(1)
}
