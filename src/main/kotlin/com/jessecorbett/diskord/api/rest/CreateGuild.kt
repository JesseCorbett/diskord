package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.Channel
import com.jessecorbett.diskord.api.ExplicitContentFilterLevel
import com.jessecorbett.diskord.api.Role
import com.jessecorbett.diskord.api.NotificationsLevel
import com.jessecorbett.diskord.api.VerificationLevel

data class CreateGuild(
        @JsonProperty("name") val name: String,
        @JsonProperty("region") val voidRegionId: String,
        @JsonProperty("icon") val base64IconData: String,
        @JsonProperty("verification_level") val verificationLevel: VerificationLevel,
        @JsonProperty("default_message_notifications") val defaultNotificationsLevel: NotificationsLevel,
        @JsonProperty("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel,
        @JsonProperty("roles") val roles: List<Role>,
        @JsonProperty("channels") val channels: List<Channel>
)
