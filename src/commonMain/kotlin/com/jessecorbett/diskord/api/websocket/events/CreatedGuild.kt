package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.model.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatedGuild(
        @SerialName("id") val id: String,
        @SerialName("name") val name: String,
        @SerialName("icon") val icon: String?,
        @SerialName("owner") val userIsOwner: Boolean,
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
        @SerialName("system_channel_id") val systemMessageChannelId: String?,
        @SerialName("joined_at") val userJoinedAt: String?,
        @SerialName("large") val isLargeGuild: Boolean?,
        @SerialName("unavailable") val isUnavailable: Boolean?,
        @SerialName("member_count") val memberCount: Int?,
        @SerialName("voice_states") val voiceStates: List<VoiceState>?,
        @SerialName("members") val members: List<GuildMember>,
        @SerialName("presences") val userPresences: List<BarePresenceUpdate>
)
