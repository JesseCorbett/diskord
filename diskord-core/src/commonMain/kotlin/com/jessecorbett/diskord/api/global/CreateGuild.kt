package com.jessecorbett.diskord.api.global

import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.api.guild.CreateChannel
import com.jessecorbett.diskord.api.guild.CreateGuildRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateGuild(
    @SerialName("name") val name: String,
    @SerialName("region") val voiceRegionId: String? = null,
    @SerialName("icon") val base64IconData: String? = null,
    @SerialName("verification_level") val verificationLevel: VerificationLevel? = null,
    @SerialName("default_message_notifications") val defaultNotificationsLevel: NotificationsLevel? = null,
    @SerialName("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel? = null,
    @SerialName("roles") val roles: List<CreateGuildRole> = emptyList(),
    @SerialName("channels") val channels: List<CreateChannel> = emptyList(),
    @SerialName("afk_channel_id") val afkChannelId: String? = null,
    @SerialName("afk_timeout") val afkTimeoutSeconds: Int? = null,
    @SerialName("system_channel_id") val systemChannelId: String? = null
)
