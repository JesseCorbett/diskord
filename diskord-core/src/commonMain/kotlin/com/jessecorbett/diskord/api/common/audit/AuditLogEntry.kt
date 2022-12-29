package com.jessecorbett.diskord.api.common.audit

import com.jessecorbett.diskord.api.common.OverwriteType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("action_type")
public sealed class AuditLogEntry {
    public abstract val id: String
    public abstract val targetId: String?
    public abstract val changes: List<AuditLogChange>
    public abstract val userId: String
    public abstract val reason: String?
}

@Serializable
@SerialName("1")
public data class GuildUpdateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("10")
public data class ChannelCreateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("11")
public data class ChannelUpdateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("12")
public data class ChannelDeleteEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("13")
public data class ChannelOverwriteCreateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null,
    @SerialName("options") public val options: Options
) : AuditLogEntry() {
    @Serializable
    public data class Options(
        @SerialName("id") val channelId: String,
        @SerialName("type") val overwriteType: OverwriteType,
        @SerialName("role_name") val roleNameForOverwriteTypeRole: String? = null
    )
}

@Serializable
@SerialName("14")
public data class ChannelOverwriteUpdateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null,
    @SerialName("options") public val options: Options
) : AuditLogEntry() {
    @Serializable
    public data class Options(
        @SerialName("id") val channelId: String,
        @SerialName("type") val overwriteType: OverwriteType,
        @SerialName("role_name") val roleNameForOverwriteTypeRole: String? = null
    )
}

@Serializable
@SerialName("15")
public data class ChannelOverwriteDeleteEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null,
    @SerialName("options") public val options: Options
) : AuditLogEntry() {
    @Serializable
    public data class Options(
        @SerialName("id") val channelId: String,
        @SerialName("type") val overwriteType: OverwriteType,
        @SerialName("role_name") val roleNameForOverwriteTypeRole: String? = null
    )
}

@Serializable
@SerialName("20")
public data class MemberKickEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("21")
public data class MemberPruneEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null,
    @SerialName("options") public val options: Options
) : AuditLogEntry() {
    @Serializable
    public data class Options(
        @SerialName("delete_member_days") val daysInactiveKickedAfter: String,
        @SerialName("members_removed") val membersRemovedCount: String
    )
}

@Serializable
@SerialName("22")
public data class MemberBanAddEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("23")
public data class MemberBanRemoveEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("24")
public data class MemberUpdateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("25")
public data class MemberRoleUpdateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("26")
public data class MemberMoveEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null,
    @SerialName("options") public val options: Options
) : AuditLogEntry() {
    @Serializable
    public data class Options(
        @SerialName("channel_id") val channelId: String,
        @SerialName("count") val count: String
    )
}

@Serializable
@SerialName("27")
public data class MemberDisconnectEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null,
    @SerialName("options") public val options: Options
) : AuditLogEntry() {
    @Serializable
    public data class Options(
        @SerialName("count") val count: String
    )
}

@Serializable
@SerialName("28")
public data class BotAddEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("30")
public data class RoleCreateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("31")
public data class RoleUpdateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("32")
public data class RoleDeleteEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("40")
public data class InviteCreateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("41")
public data class InviteUpdateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("42")
public data class InviteDeleteEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("50")
public data class WebhookCreateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("51")
public data class WebhookUpdateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("52")
public data class WebhookDeleteEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("60")
public data class EmojiCreateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("61")
public data class EmojiUpdateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("62")
public data class EmojiDeleteEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("72")
public data class MessageDeleteEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null,
    @SerialName("options") public val options: Options
) : AuditLogEntry() {
    @Serializable
    public data class Options(
        @SerialName("channel_id") val channelId: String,
        @SerialName("count") val count: String
    )
}

@Serializable
@SerialName("73")
public data class MessageBulkDeleteEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null,
    @SerialName("options") public val options: Options
) : AuditLogEntry() {
    @Serializable
    public data class Options(
        @SerialName("count") val count: String
    )
}

@Serializable
@SerialName("74")
public data class MessagePinEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null,
    @SerialName("options") public val options: Options
) : AuditLogEntry() {
    @Serializable
    public data class Options(
        @SerialName("channel_id") val channelId: String,
        @SerialName("message_id") val messageId: String
    )
}

@Serializable
@SerialName("75")
public data class MessageUnpinEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null,
    @SerialName("options") public val options: Options
) : AuditLogEntry() {
    @Serializable
    public data class Options(
        @SerialName("channel_id") val channelId: String,
        @SerialName("message_id") val messageId: String
    )
}

@Serializable
@SerialName("80")
public data class IntegrationCreateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("81")
public data class IntegrationUpdateEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()

@Serializable
@SerialName("82")
public data class IntegrationDeleteEntry(
    @SerialName("id") public override val id: String,
    @SerialName("target_id") public override val targetId: String?,
    @SerialName("changes") public override val changes: List<AuditLogChange>,
    @SerialName("user_id") public override val userId: String,
    @SerialName("reason") public override val reason: String? = null
) : AuditLogEntry()
