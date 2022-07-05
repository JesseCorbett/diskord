package com.jessecorbett.diskord.api.common

import com.jessecorbett.diskord.internal.CodeEnum
import com.jessecorbett.diskord.internal.CodeEnumSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Guild(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon") val iconHash: String?,
    @SerialName("splash") val splashHash: String?,
    @SerialName("discovery_splash") val discoverySplashHash: String?,
    @SerialName("owner") val userIsOwner: Boolean? = null,
    @SerialName("owner_id") val ownerId: String,
    @SerialName("permissions") val permissions: Permissions? = null,
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
    @SerialName("max_presences") val maxPresences: Int? = null,
    @SerialName("max_members") val maxMembers: Int? = null,
    @SerialName("vanity_url_code") val vanityCodeUrl: String?,
    @SerialName("description") val description: String?,
    @SerialName("banner") val bannerHash: String?,
    @SerialName("premium_tier") val premiumType: GuildPremiumType,
    @SerialName("premium_subscription_count") val guildBoostCount: Int? = null,
    @SerialName("preferred_locale") val preferredLocale: String,
    @SerialName("public_updates_channel_id") val publicUpdatesChannelId: String?,
    @SerialName("max_video_channel_users") val maxVideoChannelUsers: Int? = null,
    @SerialName("approximate_member_count") val approximateMemberCount: Int? = null,
    @SerialName("approximate_presence_count") val approximatePresenceCount: Int? = null,
    @SerialName("welcome_screen") val welcomeScreen: WelcomeScreen? = null,
    @SerialName("nsfw_level") val nsfwLevel: GuildNSFWLevel,
    @SerialName("stage_instances") val stageInstances: List<StageInstance>? = null,
    @SerialName("stickers") val sticker: List<Sticker>? = null,
)

@Serializable(with = VerificationLevelSerializer::class)
public enum class VerificationLevel(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    NONE(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4)
}

public class VerificationLevelSerializer : CodeEnumSerializer<VerificationLevel>(VerificationLevel.UNKNOWN, VerificationLevel.values())

@Serializable(with = NotificationsLevelSerializer::class)
public enum class NotificationsLevel(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    ALL_MESSAGES(0),
    ONLY_MENTIONS(1)
}

public class NotificationsLevelSerializer : CodeEnumSerializer<NotificationsLevel>(NotificationsLevel.UNKNOWN, NotificationsLevel.values())

@Serializable(with = ExplicitContentFilterLevelSerializer::class)
public enum class ExplicitContentFilterLevel(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    DISABLED(0),
    MEMBERS_WITHOUT_ROLES(1),
    ALL_MEMBERS(2)
}

public class ExplicitContentFilterLevelSerializer : CodeEnumSerializer<ExplicitContentFilterLevel>(ExplicitContentFilterLevel.UNKNOWN, ExplicitContentFilterLevel.values())

public enum class GuildFeatures {
    ANIMATED_ICON,
    BANNER,
    COMMERCE,
    COMMUNITY,
    DISCOVERABLE,
    FEATURABLE,
    INVITE_SPLASH,
    MEMBER_VERIFICATION_GATE_ENABLED,
    NEWS,
    PARTNERED,
    PREVIEW_ENABLED,
    VANITY_URL,
    VERIFIED,
    VIP_REGIONS,
    WELCOME_SCREEN_ENABLED,
    TICKETED_EVENTS_ENABLED,
    MONETIZATION_ENABLED,
    MORE_STICKERS,
    THREE_DAY_THREAD_ARCHIVE,
    SEVEN_DAY_THREAD_ARCHIVE,
    PRIVATE_THREADS
}

@Serializable(with = GuildPremiumTypeSerializer::class)
public enum class GuildPremiumType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    NONE(0),
    TIER_1(1),
    TIER_2(2),
    TIER_3(3)
}

public class GuildPremiumTypeSerializer : CodeEnumSerializer<GuildPremiumType>(GuildPremiumType.UNKNOWN, GuildPremiumType.values())

@Serializable(with = MFALevelSerializer::class)
public enum class MFALevel(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    NONE(0),
    ELEVATED(1)
}

public class MFALevelSerializer : CodeEnumSerializer<MFALevel>(MFALevel.UNKNOWN, MFALevel.values())

@Serializable
public data class WelcomeScreen(
    @SerialName("description") val description: String?,
    @SerialName("welcome_channels") val welcomeChannels: List<WelcomeScreenChannel>
)

@Serializable
public data class WelcomeScreenChannel(
    @SerialName("channel_id") val channelId: String,
    @SerialName("description") val description: String,
    @SerialName("emoji_id") val emojiId: String?,
    @SerialName("emoji_name") val emojiName: String?
)

@Serializable(with = GuildNSFWLevelSerializer::class)
public enum class GuildNSFWLevel(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    DEFAULT(0),
    EXPLICIT(1),
    SAFE(2),
    AGE_RESTRICTED(3)
}

public class GuildNSFWLevelSerializer : CodeEnumSerializer<GuildNSFWLevel>(GuildNSFWLevel.UNKNOWN, GuildNSFWLevel.values())

@Serializable
public data class StageInstance(
    @SerialName("id") val id: String,
    @SerialName("guild_id") val guildId: String,
    @SerialName("channel_id") val channelId: String,
    @SerialName("topic") val topic: String,
    @SerialName("privacy_level") val privacyLevel: StagePrivacyLevel,
    @SerialName("discoverable_disabled") val discoverable_disabled: Boolean,
)

@Serializable(with = StagePrivacyLevelSerializer::class)
public enum class StagePrivacyLevel(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    PUBLIC(1),
    GUILD_ONLY(2)
}

public class StagePrivacyLevelSerializer : CodeEnumSerializer<StagePrivacyLevel>(StagePrivacyLevel.UNKNOWN, StagePrivacyLevel.values())
