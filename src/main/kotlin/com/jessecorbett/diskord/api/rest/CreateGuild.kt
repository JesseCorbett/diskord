package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.models.Channel
import com.jessecorbett.diskord.api.models.ExplicitContentFilterLevel
import com.jessecorbett.diskord.api.models.Role
import com.jessecorbett.diskord.api.models.values.NotificationsLevel
import com.jessecorbett.diskord.api.models.values.VerificationLevel

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
