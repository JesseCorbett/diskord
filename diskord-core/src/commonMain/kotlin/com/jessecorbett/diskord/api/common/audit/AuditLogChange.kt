package com.jessecorbett.diskord.api.common.audit

import com.jessecorbett.diskord.api.common.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("key")
public sealed class AuditLogChange

@Serializable
@SerialName("name")
public data class GuildNameChange(
    @SerialName("old_value") val oldName: String? = null,
    @SerialName("new_value") val newName: String? = null
) : AuditLogChange()

@Serializable
@SerialName("icon_hash")
public data class GuildIconChange(
    @SerialName("old_value") val oldHash: String? = null,
    @SerialName("new_value") val newHash: String? = null
) : AuditLogChange()

@Serializable
@SerialName("splash_hash")
public data class GuildInviteSplashChange(
    @SerialName("old_value") val oldHash: String? = null,
    @SerialName("new_value") val newHash: String? = null
) : AuditLogChange()

@Serializable
@SerialName("owner_id")
public data class GuildOwnerChange(
    @SerialName("old_value") val oldOwner: String? = null,
    @SerialName("new_value") val newOwner: String? = null
) : AuditLogChange()

@Serializable
@SerialName("region")
public data class GuildRegionChange(
    @SerialName("old_value") val oldRegion: String? = null,
    @SerialName("new_value") val newRegion: String? = null
) : AuditLogChange()

@Serializable
@SerialName("afk_channel_id")
public data class GuildAfkChannelChange(
    @SerialName("old_value") val oldChannelId: String? = null,
    @SerialName("new_value") val newChannelId: String? = null
) : AuditLogChange()

@Serializable
@SerialName("afk_timeout")
public data class GuildAfkTimeoutChange(
    @SerialName("old_value") val oldTimeout: Int? = null,
    @SerialName("new_value") val newTimeout: Int? = null
) : AuditLogChange()

@Serializable
@SerialName("mfa_level")
public data class GuildMultiFactorAuthenticationChange(
    @SerialName("old_value") val oldMFARequirement: MFALevel? = null,
    @SerialName("new_value") val newMFARequirement: MFALevel? = null
) : AuditLogChange()

@Serializable
@SerialName("verification_level")
public data class GuildVerificationRequirementChange(
    @SerialName("old_value") val oldVerificationLevel: VerificationLevel? = null,
    @SerialName("new_value") val newVerificationLevel: VerificationLevel? = null
) : AuditLogChange()

@Serializable
@SerialName("explicit_content_filter")
public data class GuildExplicitContentFilterChange(
    @SerialName("old_value") val oldFilterLevel: ExplicitContentFilterLevel? = null,
    @SerialName("new_value") val newFilterLevel: ExplicitContentFilterLevel? = null
) : AuditLogChange()

@Serializable
@SerialName("default_message_notifications")
public data class GuildDefaultNotificationLevelChange(
    @SerialName("old_value") val oldDefault: NotificationsLevel? = null,
    @SerialName("new_value") val newDefault: NotificationsLevel? = null
) : AuditLogChange()

@Serializable
@SerialName("vanity_url_code")
public data class GuildVanityUrlCodeChange(
    @SerialName("old_value") val oldVanityCode: String? = null,
    @SerialName("new_value") val newVanityCode: String? = null
) : AuditLogChange()

@Serializable
@SerialName("\$add")
public data class GuildRoleAddedChange(
    @SerialName("old_value") val oldRoles: List<AuditRolePartial>? = null,
    @SerialName("new_value") val newRoles: List<AuditRolePartial>? = null
) : AuditLogChange()

@Serializable
@SerialName("\$remove")
public data class GuildRoleRemoveChange(
    @SerialName("old_value") val oldRoles: List<AuditRolePartial>? = null,
    @SerialName("new_value") val newRoles: List<AuditRolePartial>? = null
) : AuditLogChange()

@Serializable
public data class AuditRolePartial(val id: String, val name: String)

@Serializable
@SerialName("prune_delete_days")
public data class GuildPruneDaysChange(
    @SerialName("old_value") val oldPruneDays: Int? = null,
    @SerialName("new_value") val newPruneDays: Int? = null
) : AuditLogChange()

@Serializable
@SerialName("widget_enabled")
public data class GuildWidgetEnabledChange(
    @SerialName("old_value") val wasEnabled: Boolean? = null,
    @SerialName("new_value") val isEnabled: Boolean? = null
) : AuditLogChange()

@Serializable
@SerialName("widget_channel_id")
public data class GuildWidgetChannelIdChange(
    @SerialName("old_value") val oldChannelId: String? = null,
    @SerialName("new_value") val newChannelId: String? = null
) : AuditLogChange()

@Serializable
@SerialName("system_channel_id")
public data class GuildSystemChannelIdChange(
    @SerialName("old_value") val oldChannelId: String? = null,
    @SerialName("new_value") val newChannelId: String? = null
) : AuditLogChange()

@Serializable
@SerialName("position")
public data class ChannelPositionChange(
    @SerialName("old_value") val oldPosition: Int? = null,
    @SerialName("new_value") val newPosition: Int? = null
) : AuditLogChange()

@Serializable
@SerialName("topic")
public data class ChannelTopicChange(
    @SerialName("old_value") val oldTopic: String? = null,
    @SerialName("new_value") val newTopic: String? = null
) : AuditLogChange()

@Serializable
@SerialName("bitrate")
public data class VoiceChannelBitrateChange(
    @SerialName("old_value") val oldBitrate: Int? = null,
    @SerialName("new_value") val newBitrate: Int? = null
) : AuditLogChange()

@Serializable
@SerialName("permission_overwrites")
public data class ChannelPermissionsChange(
    @SerialName("old_value") val oldPermissions: List<Overwrite>? = null,
    @SerialName("new_value") val newPermissions: List<Overwrite>? = null
) : AuditLogChange()

@Serializable
@SerialName("nsfw")
public data class ChannelIsNSFWChange(
    @SerialName("old_value") val wasNSFW: Boolean? = null,
    @SerialName("new_value") val isNSFW: Boolean? = null
) : AuditLogChange()

/**
 * It's unclear the difference between new and old on this one, since this event encapsulates both add and remove
 */
@Serializable
@SerialName("application_id")
public data class ChannelApplicationIdChange(
    @SerialName("old_value") val oldApplicationId: String? = null,
    @SerialName("new_value") val newApplicationId: String? = null
) : AuditLogChange()

@Serializable
@SerialName("rate_limit_per_user")
public data class ChannelRatLimitPerUserChange(
    @SerialName("old_value") val oldLimit: Int? = null,
    @SerialName("new_value") val newLimit: Int? = null
) : AuditLogChange()

@Serializable
@SerialName("permissions")
public data class RolePermissionsChange(
    @SerialName("old_value") val oldPermissions: Permissions? = null,
    @SerialName("new_value") val newPermissions: Permissions? = null
) : AuditLogChange()

@Serializable
@SerialName("color")
public data class RoleColorChange(
    @SerialName("old_value") val oldColor: Color? = null,
    @SerialName("new_value") val newColor: Color? = null
) : AuditLogChange()

@Serializable
@SerialName("hoist")
public data class RoleHoistChange(
    @SerialName("old_value") val wasDisplayedSeparate: Boolean? = null,
    @SerialName("new_value") val isDisplayedSeparate: Boolean? = null
) : AuditLogChange()

@Serializable
@SerialName("mentionable")
public data class RoleMentionableChange(
    @SerialName("old_value") val wasMentionable: Boolean? = null,
    @SerialName("new_value") val isMentionable: Boolean? = null
) : AuditLogChange()

@Serializable
@SerialName("allow")
public data class RoleAllowChange(
    @SerialName("old_value") val oldAllow: String? = null, // TODO: is this a permissions object or what? = null
    @SerialName("new_value") val newAllow: String? = null
) : AuditLogChange()

@Serializable
@SerialName("deny")
public data class RoleDenyChange(
    @SerialName("old_value") val oldDeny: String? = null,
    @SerialName("new_value") val newDeny: String? = null
) : AuditLogChange()

@Serializable
@SerialName("code")
public data class InviteCodeChange(
    @SerialName("old_value") val oldCode: String? = null,
    @SerialName("new_value") val newCode: String? = null
) : AuditLogChange()

@Serializable
@SerialName("channel_id")
public data class InviteChannelIdChange(
    @SerialName("old_value") val oldChannelId: String? = null,
    @SerialName("new_value") val newChannelId: String? = null
) : AuditLogChange()

@Serializable
@SerialName("inviter_id")
public data class InviteInviterChange(
    @SerialName("old_value") val oldInviterId: String? = null,
    @SerialName("new_value") val newInviterId: String? = null
) : AuditLogChange()

@Serializable
@SerialName("max_uses")
public data class InviteMaxUsersChange(
    @SerialName("old_value") val oldMax: Int? = null,
    @SerialName("new_value") val newMax: Int? = null
) : AuditLogChange()

@Serializable
@SerialName("uses")
public data class InviteUsesChange(
    @SerialName("old_value") val oldUses: Int? = null,
    @SerialName("new_value") val newUses: Int? = null
) : AuditLogChange()

@Serializable
@SerialName("max_age")
public data class InviteMaxAgeChange(
    @SerialName("old_value") val oldMax: Int? = null,
    @SerialName("new_value") val newMax: Int? = null
) : AuditLogChange()

@Serializable
@SerialName("temporary")
public data class InviteIsTemporaryChange(
    @SerialName("old_value") val wasTemporary: Boolean? = null,
    @SerialName("new_value") val isTemporary: Boolean? = null
) : AuditLogChange()

@Serializable
@SerialName("deaf")
public data class UserIsDeafChange(
    @SerialName("old_value") val wasDeaf: Boolean? = null,
    @SerialName("new_value") val isDeaf: Boolean? = null
) : AuditLogChange()

@Serializable
@SerialName("mute")
public data class UserIsMuteChange(
    @SerialName("old_value") val wasMute: Boolean? = null,
    @SerialName("new_value") val isMute: Boolean? = null
) : AuditLogChange()

@Serializable
@SerialName("nick")
public data class UserNicknameChange(
    @SerialName("old_value") val oldNickname: String? = null,
    @SerialName("new_value") val newNickname: String? = null
) : AuditLogChange()

@Serializable
@SerialName("avatar_hash")
public data class UserAvatarChange(
    @SerialName("old_value") val oldHash: String? = null,
    @SerialName("new_value") val newHash: String? = null
) : AuditLogChange()

@Serializable
@SerialName("id")
public data class AnyIdChange(
    @SerialName("old_value") val oldId: String? = null,
    @SerialName("new_value") val newId: String? = null
) : AuditLogChange()

/**
 * May be deserializable to [ChannelType] if the object in question is a [Channel]
 */
@Serializable
@SerialName("type")
public data class AnyTypeChange(
    @SerialName("old_value") val oldType: String? = null,
    @SerialName("new_value") val newType: String? = null
) : AuditLogChange()

/**
 * Whether emoticons get synced with an integrations
 *
 * Twitch only currently
 */
@Serializable
@SerialName("enable_emoticons")
public data class IntegrationEmoticonsSyncedChange(
    @SerialName("old_value") val wasEmoticonsSynced: Boolean? = null,
    @SerialName("new_value") val isEmoticonsSynced: Boolean? = null
) : AuditLogChange()

@Serializable
@SerialName("expire_behavior")
public data class IntegrationExpireBehaviorChange(
    @SerialName("old_value") val oldBehavior: IntegrationExpireBehavior? = null,
    @SerialName("new_value") val newBehavior: IntegrationExpireBehavior? = null
) : AuditLogChange()

@Serializable
@SerialName("expire_grace_period")
public data class IntegrationExpireGracePeriodChange(
    @SerialName("old_value") val oldGracePeriod: Int? = null,
    @SerialName("new_value") val newGracePeriod: Int? = null
) : AuditLogChange()
