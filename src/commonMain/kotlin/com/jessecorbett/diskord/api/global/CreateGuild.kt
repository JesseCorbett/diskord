package com.jessecorbett.diskord.api.global

import com.jessecorbett.diskord.api.common.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateGuild(
    @SerialName("name") val name: String,
    @SerialName("region") val voiceRegionId: String,
    @SerialName("icon") val base64IconData: String,
    @SerialName("verification_level") val verificationLevel: VerificationLevel,
    @SerialName("default_message_notifications") val defaultNotificationsLevel: NotificationsLevel,
    @SerialName("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel,
    @SerialName("roles") val roles: List<Role>,
    @SerialName("channels") val channels: List<Channel>
)
