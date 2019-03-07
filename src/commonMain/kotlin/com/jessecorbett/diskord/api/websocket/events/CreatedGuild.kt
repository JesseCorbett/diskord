package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.model.*
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatedGuild(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @Optional @SerialName("icon") val icon: String? = null,
    @Optional @SerialName("owner") val userIsOwner: Boolean = false,
    @SerialName("owner_id") val ownerId: String,
    @Optional @SerialName("permissions") val permissions: Int? = null,
    @SerialName("region") val region: String,
    @Optional @SerialName("afk_channel_id") val afkChannelId: String? = null,
    @SerialName("afk_timeout") val afkTimeoutSeconds: Int,
    @Optional @SerialName("embed_enabled") val embedEnabled: Boolean? = null,
    @Optional @SerialName("embed_channel_id") val embeddedChannelId: String? = null,
    @SerialName("verification_level") val verificationLevel: VerificationLevel,
    @SerialName("default_message_notifications") val defaultMessageNotificationLevel: NotificationsLevel,
    @SerialName("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel,
    @SerialName("roles") val roles: List<Role>,
    @SerialName("emojis") val emojis: List<Emoji>,
    @SerialName("features") val features: List<String>,
    @SerialName("mfa_level") val mfaLevel: MFALevel,
    @Optional @SerialName("application_id") val owningApplicationId: String? = null,
    @Optional @SerialName("widget_enabled") val widgetEnabled: Boolean? = null,
    @Optional @SerialName("widget_channel_id") val widgetChannelId: String? = null,
    @Optional @SerialName("system_channel_id") val systemMessageChannelId: String? = null,
    @Optional @SerialName("joined_at") val userJoinedAt: String? = null,
    @Optional @SerialName("large") val isLargeGuild: Boolean? = null,
    @Optional @SerialName("unavailable") val isUnavailable: Boolean? = null,
    @Optional @SerialName("member_count") val memberCount: Int? = null,
    @SerialName("voice_states") val voiceStates: List<VoiceState>,
    @SerialName("members") val members: List<GuildMember>,
    @SerialName("presences") val userPresences: List<BarePresenceUpdate>
)
