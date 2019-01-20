package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.model.ExplicitContentFilterLevel
import com.jessecorbett.diskord.api.model.NotificationsLevel
import com.jessecorbett.diskord.api.model.VerificationLevel

data class PatchGuild(
        @JsonProperty("name") val name: String? = null,
        @JsonProperty("region") val voiceRegionId: String? = null,
        @JsonProperty("verification_level") val verificationLevel: VerificationLevel? = null,
        @JsonProperty("default_message_notifications") val defaultNotificationsLevel: NotificationsLevel? = null,
        @JsonProperty("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel? = null,
        @JsonProperty("afk_channel_id") val afkChannelId: String? = null,
        @JsonProperty("afk_timeout") val afkTimeoutSeconds: Int? = null,
        @JsonProperty("icon") val base64IconData: String? = null,
        @JsonProperty("owner_id") val ownerId: String? = null,
        @JsonProperty("splash") val base64SplashData: String? = null,
        @JsonProperty("system_channel_id") val systemChannelId: String? = null
)
