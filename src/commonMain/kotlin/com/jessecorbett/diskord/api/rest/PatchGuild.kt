package com.jessecorbett.diskord.api.rest

import com.jessecorbett.diskord.api.model.ExplicitContentFilterLevel
import com.jessecorbett.diskord.api.model.NotificationsLevel
import com.jessecorbett.diskord.api.model.VerificationLevel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchGuild(
        @SerialName("name") val name: String? = null,
        @SerialName("region") val voiceRegionId: String? = null,
        @SerialName("verification_level") val verificationLevel: VerificationLevel? = null,
        @SerialName("default_message_notifications") val defaultNotificationsLevel: NotificationsLevel? = null,
        @SerialName("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel? = null,
        @SerialName("afk_channel_id") val afkChannelId: String? = null,
        @SerialName("afk_timeout") val afkTimeoutSeconds: Int? = null,
        @SerialName("icon") val base64IconData: String? = null,
        @SerialName("owner_id") val ownerId: String? = null,
        @SerialName("splash") val base64SplashData: String? = null,
        @SerialName("system_channel_id") val systemChannelId: String? = null
)
