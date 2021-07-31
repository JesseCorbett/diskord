package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreatedGuild(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon") val iconHash: String?,
    @SerialName("splash") val splashHash: String?,
    @SerialName("discovery_splash") val discoverySplashHash: String?,
    @SerialName("owner") val userIsOwner: Boolean? = null,
    @SerialName("owner_id") val ownerId: String,
    @SerialName("permissions") val permissions: Permissions? = null,
    @SerialName("region") val region: String, // FIXME: API docs says this is removed?
    @SerialName("afk_channel_id") val afkChannelId: String?,
    @SerialName("afk_timeout") val afkTimeoutSeconds: Int,
    @SerialName("widget_enabled") val widgetEnabled: Boolean? = null,
    @SerialName("widget_channel_id") val widgetChannelId: String? = null,
    @SerialName("verification_level") val verificationLevel: VerificationLevel,
    @SerialName("default_message_notifications") val defaultMessageNotificationLevel: NotificationsLevel,
    @SerialName("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel,
    @SerialName("roles") val roles: List<Role>,
    @SerialName("emojis") val emojis: List<Emoji>,
    @SerialName("features") val features: List<GuildFeatures>,
    @SerialName("mfa_level") val mfaLevel: MFALevel,
    @SerialName("application_id") val owningApplicationId: String?,
    @SerialName("system_channel_id") val systemMessageChannelId: String?,
    @SerialName("system_channel_flags") val systemChannelFlags: SystemChannelFlags,
    @SerialName("rules_channel_id") val rulesChannelId: String?,
    @SerialName("joined_at") val joinedAt: String? = null,
    @SerialName("large") val isLarge: Boolean? = null,
    @SerialName("unavailable") val isUnavailable: Boolean? = null,
    @SerialName("member_count") val memberCount: Int?,
    @SerialName("voice_states") val voiceStates: List<VoiceState>? = null,
    @SerialName("members") val members: List<GuildMember>? = null,
    @SerialName("channels") val channels: List<Channel>? = null,
    @SerialName("presences") val presences: List<PresenceUpdate>? = null,
    @SerialName("max_presences") val maxPresences: Int? = null,
    @SerialName("max_members") val maxMembers: Int? = null,
    @SerialName("vanity_url_code") val vanityCodeUrl: String?,
    @SerialName("description") val description: String?,
    @SerialName("banner") val bannerHash: String?,
    @SerialName("premium_tier") val premiumType: PremiumType,
    @SerialName("premium_subscription_count") val guildBoostCount: Int? = null,
    @SerialName("preferred_locale") val preferredLocale: String,
    @SerialName("public_updates_channel_id") val publicUpdatesChannelId: String?,
    @SerialName("max_video_channel_users") val maxVideoChannelUsers: Int? = null,
    @SerialName("approximate_member_count") val approximateMemberCount: Int? = null,
    @SerialName("approximate_presence_count") val approximatePresenceCount: Int? = null,
    @SerialName("welcome_screen") val welcomeScreen: WelcomeScreen? = null,
    @SerialName("nsfw_level") val nsfwLevel: GuildNSFWLevel,
    @SerialName("stage_instances") val stageInstances: List<StageInstance>? = null,
    @SerialName("stickers") val sticker: List<Sticker>? = null
)
