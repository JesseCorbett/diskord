package com.jessecorbett.diskord.api.model

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement

@Serializable
data class AuditLog(
    @SerialName("webhooks") val webhooks: List<Webhook>,
    @SerialName("users") val users: List<User>,
    @SerialName("audit_log_entries") val entries: List<AuditLogEntry>
)

@Serializable
data class AuditLogEntry(
    @SerialName("id") val id: String,
    @SerialName("target_id") val targetId: String?,
    @SerialName("changes") val changes: List<AuditLogChange> = emptyList(),
    @SerialName("user_id") val userId: String,
    @SerialName("action_type") val actionType: Int,
    @SerialName("options") val optionalData: OptionalEntryData? = null,
    @SerialName("reason") val reason: String? = null
)

// TODO: Make super dynamic and all https://discordapp.com/developers/docs/resources/audit-log#audit-log-change-object
@Serializable
data class AuditLogChange(
    @SerialName("new_value") val newValue: JsonElement? = null,
    @SerialName("old_value") val oldValue: JsonElement? = null,
    @SerialName("key") val key: String
)

@Serializable
data class OptionalEntryData(
    @SerialName("delete_member_days") val pruneKickedAfterDays: String?,
    @SerialName("members_removed") val pruneMembersPrunedCount: String?,
    @SerialName("channel_id") val deleteChannelId: String?,
    @SerialName("count") val deleteMessageCount: String?,
    @SerialName("id") val overwriteEntityId: String?,
    @SerialName("type") val overwriteEntityType: OverwrittenEntityType?,
    @SerialName("role_name") val overwriteRoleName: String?
)

@Serializable
enum class OverwrittenEntityType {
    @SerialName("member") MEMBER,
    @SerialName("role") ROLE
}

@Serializable
enum class AuditLogActionType {
    @SerialName("1") GUILD_UPDATE,
    @SerialName("10") CHANNEL_CREATE,
    @SerialName("11") CHANNEL_UPDATE,
    @SerialName("12") CHANNEL_DELETE,
    @SerialName("13") CHANNEL_OVERWRITE_CREATE,
    @SerialName("14") CHANNEL_OVERWRITE_UPDATE,
    @SerialName("15") CHANNEL_OVERWRITE_DELETE,
    @SerialName("20") MEMBER_KICK,
    @SerialName("21") MEMBER_PRUNE,
    @SerialName("22") MEMBER_BAN_ADD,
    @SerialName("23") MEMBER_BAN_REMOVE,
    @SerialName("24")  MEMBER_UPDATE,
    @SerialName("25") MEMBER_ROLE_UPDATE,
    @SerialName("26")  MEMBER_MOVE,
    @SerialName("27")   MEMBER_DISCONNECT,
    @SerialName("28")  BOT_ADD,
    @SerialName("30")  ROLE_CREATE,
    @SerialName("31")  ROLE_UPDATE,
    @SerialName("32")  ROLE_DELETE,
    @SerialName("40")   INVITE_CREATE,
    @SerialName("41")  INVITE_UPDATE,
    @SerialName("42")  INVITE_DELETE,
    @SerialName("50")  WEBHOOK_CREATE,
    @SerialName("51")   WEBHOOK_UPDATE,
    @SerialName("52")   WEBHOOK_DELETE,
    @SerialName("60")  EMOJI_CREATE,
    @SerialName("61")  EMOJI_UPDATE,
    @SerialName("62")  EMOJI_DELETE,
    @SerialName("72")  MESSAGE_DELETE,
    @SerialName("73")  MESSAGE_BULK_DELETE,
    @SerialName("74")   MESSAGE_PIN,
    @SerialName("75")   MESSAGE_UNPIN,
    @SerialName("80")   INTEGRATION_CREATE,
    @SerialName("81")   INTEGRATION_UPDATE,
    @SerialName("82")   INTEGRATION_DELETE
}
