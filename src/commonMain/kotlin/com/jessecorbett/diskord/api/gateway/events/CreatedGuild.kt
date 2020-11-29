package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreatedGuild(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon") val icon: String? = null,
    @SerialName("owner") val userIsOwner: Boolean = false,
    @SerialName("owner_id") val ownerId: String,
    @SerialName("permissions") val permissions: Permissions? = null,
    @SerialName("region") val region: String,
    @SerialName("afk_channel_id") val afkChannelId: String? = null,
    @SerialName("afk_timeout") val afkTimeoutSeconds: Int,
    @SerialName("embed_enabled") val embedEnabled: Boolean? = null,
    @SerialName("embed_channel_id") val embeddedChannelId: String? = null,
    @SerialName("verification_level") val verificationLevel: VerificationLevel,
    @SerialName("default_message_notifications") val defaultMessageNotificationLevel: NotificationsLevel,
    @SerialName("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel,
    @SerialName("roles") val roles: List<Role>,
    @SerialName("emojis") val emojis: List<Emoji>,
    @SerialName("features") val features: List<String>,
    @SerialName("mfa_level") val mfaLevel: MFALevel,
    @SerialName("application_id") val owningApplicationId: String? = null,
    @SerialName("widget_enabled") val widgetEnabled: Boolean? = null,
    @SerialName("widget_channel_id") val widgetChannelId: String? = null,
    @SerialName("system_channel_id") val systemMessageChannelId: String? = null,
    @SerialName("joined_at") val userJoinedAt: String? = null,
    @SerialName("large") val isLargeGuild: Boolean? = null,
    @SerialName("unavailable") val isUnavailable: Boolean? = null,
    @SerialName("member_count") val memberCount: Int? = null,
    @SerialName("voice_states") val voiceStates: List<VoiceState>,
    @SerialName("members") val members: List<GuildMember>,
    @SerialName("channels") val channels: List<Channel>,
    @SerialName("presences") val userPresences: List<BarePresenceUpdate>
)
