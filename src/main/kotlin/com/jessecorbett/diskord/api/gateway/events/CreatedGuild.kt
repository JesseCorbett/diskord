package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.Emoji
import com.jessecorbett.diskord.api.Role
import com.jessecorbett.diskord.api.models.*
import com.jessecorbett.diskord.api.models.values.MFALevel
import com.jessecorbett.diskord.api.models.values.NotificationsLevel
import com.jessecorbett.diskord.api.models.values.VerificationLevel
import java.time.ZonedDateTime

data class CreatedGuild(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("icon") val icon: String?,
        @JsonProperty("owner") val userIsOwner: Boolean,
        @JsonProperty("owner_id") val ownerId: String,
        @JsonProperty("permissions") val permissions: Int?,
        @JsonProperty("region") val region: String,
        @JsonProperty("afk_channel_id") val afkChannelId: String?,
        @JsonProperty("afk_timeout") val afkTimeoutSeconds: Int,
        @JsonProperty("embed_enabled") val embedEnabled: Boolean?,
        @JsonProperty("embed_channel_id") val embeddedChannelId: String?,
        @JsonProperty("verification_level") val verificationLevel: VerificationLevel,
        @JsonProperty("default_message_notifications") val defaultMessageNotificationLevel: NotificationsLevel,
        @JsonProperty("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel,
        @JsonProperty("roles") val roles: Array<Role>,
        @JsonProperty("emojis") val emojis: Array<Emoji>,
        @JsonProperty("features") val features: Array<String>,
        @JsonProperty("mfa_level") val mfaLevel: MFALevel,
        @JsonProperty("application_id") val owningApplicationId: String?,
        @JsonProperty("widget_enabled") val widgetEnabled: Boolean?,
        @JsonProperty("widget_channel_id") val widgetChannelId: String?,
        @JsonProperty("system_channel_id") val systemMessageChannelId: String?,
        @JsonProperty("joined_at") val userJoinedAt: ZonedDateTime?,
        @JsonProperty("large") val isLargeGuild: Boolean?,
        @JsonProperty("unavailable") val isUnavailable: Boolean?,
        @JsonProperty("member_count") val memberCount: Int?,
        @JsonProperty("voice_states") val voiceStates: Array<VoiceState>?,
        @JsonProperty("members") val members: Array<GuildMember>,
        @JsonProperty("presences") val userPresences: Array<BarePresenceUpdate>
)
