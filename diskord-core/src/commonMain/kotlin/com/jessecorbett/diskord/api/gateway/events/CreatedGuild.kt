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
    @SerialName("channels") val channels: List<CreatedChannel>? = null,
    @SerialName("threads") val threads: List<GuildThread>? = null,
    @SerialName("presences") val presences: List<PresenceUpdate>? = null,
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

@Serializable
public sealed class CreatedChannel {
    public abstract val id: String
}

public interface CreatedGuildChannel : NamedChannel {
    public val guildId: String?
    public val position: Int
    public val nsfw: Boolean?
    public val permissionOverwrites: List<Overwrite>
}

public interface CreatedGuildText : CreatedGuildChannel, TextChannel {
    public val topic: String?
    public val rateLimitPerUser: Int?
    public val parentId: String?
}

@Serializable
public sealed class CreatedGuildThread : CreatedChannel(), Thread {
    abstract override val id: String
    abstract override val name: String
    abstract override val lastMessageId: String?
    abstract override val lastPinTime: String?
    abstract override val guildId: String
    abstract override val rateLimitPerUser: Int?
    abstract override val ownerId: String?
    abstract override val parentId: String?
    abstract override val messageCount: Int
    abstract override val memberCount: Int
    abstract override val metadata: ThreadMetadata?
    abstract override val member: ThreadMember?
}

@Serializable
@SerialName("0")
public data class CreatedGuildTextChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("topic") override val topic: String? = null,
    @SerialName("nsfw") override val nsfw: Boolean? = null,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("parent_id") override val parentId: String? = null,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null
) : CreatedChannel(), CreatedGuildText

@Serializable
@SerialName("1")
public data class CreatedDMChannel(
    @SerialName("id") override val id: String,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("recipients") override val recipients: List<User>,
    @SerialName("owner_id") override val ownerId: String? = null,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null
) : CreatedChannel(), DM

@Serializable
@SerialName("2")
public data class CreatedGuildVoiceChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("bitrate") val bitrate: Int,
    @SerialName("user_limit") val userLimit: Int,
    @SerialName("parent_id") val parentId: String? = null,
    @SerialName("nsfw") override val nsfw: Boolean? = null,
    @SerialName("rtc_region") val rtcRegion: String? = null,
) : CreatedChannel(), CreatedGuildChannel

@Serializable
@SerialName("3")
public data class CreatedGroupDMChannel(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("recipients") override val recipients: List<User>,
    @SerialName("icon") val iconHash: String?,
    @SerialName("owner_id") override val ownerId: String,
    @SerialName("application_id") val applicationId: String?,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null
) : CreatedChannel(), NamedChannel, DM

@Serializable
@SerialName("4")
public data class CreatedGuildCategory(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("nsfw") override val nsfw: Boolean? = null,
) : CreatedChannel(), CreatedGuildChannel

@Serializable
@SerialName("5")
public data class CreatedGuildNewsChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("topic") override val topic: String? = null,
    @SerialName("nsfw") override val nsfw: Boolean? = null,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("parent_id") override val parentId: String? = null,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null
) : CreatedChannel(), CreatedGuildText

@Serializable
@SerialName("6")
public data class CreatedGuildStoreChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("nsfw") override val nsfw: Boolean? = null,
    @SerialName("parent_id") val parentId: String? = null,
) : CreatedChannel(), CreatedGuildChannel

@Serializable
@SerialName("10")
public data class CreatedGuildNewsThread(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null,
    @SerialName("guild_id") override val guildId: String,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("owner_id") override val ownerId: String?,
    @SerialName("parent_id") override val parentId: String? = null,
    @SerialName("message_count") override val messageCount: Int,
    @SerialName("member_count") override val memberCount: Int,
    @SerialName("thread_metadata") override val metadata: ThreadMetadata? = null,
    @SerialName("member") override val member: ThreadMember? = null,
) : CreatedGuildThread()

@Serializable
@SerialName("11")
public data class CreatedGuildPublicThread(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null,
    @SerialName("guild_id") override val guildId: String,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("owner_id") override val ownerId: String?,
    @SerialName("parent_id") override val parentId: String? = null,
    @SerialName("message_count") override val messageCount: Int,
    @SerialName("member_count") override val memberCount: Int,
    @SerialName("thread_metadata") override val metadata: ThreadMetadata? = null,
    @SerialName("member") override val member: ThreadMember? = null,
) : CreatedGuildThread()

@Serializable
@SerialName("12")
public data class CreatedGuildPrivateThread(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null,
    @SerialName("guild_id") override val guildId: String,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("owner_id") override val ownerId: String?,
    @SerialName("parent_id") override val parentId: String? = null,
    @SerialName("message_count") override val messageCount: Int,
    @SerialName("member_count") override val memberCount: Int,
    @SerialName("thread_metadata") override val metadata: ThreadMetadata? = null,
    @SerialName("member") override val member: ThreadMember? = null,
) : CreatedGuildThread()

