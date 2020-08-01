package com.jessecorbett.diskord.api.model

import kotlinx.serialization.*
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

@Serializable(with = AuditLogActionTypeSerializer::class)
enum class AuditLogActionType(val code: Int) {
    GUILD_UPDATE(1),
    CHANNEL_CREATE(10),
    CHANNEL_UPDATE(11),
    CHANNEL_DELETE(12),
    CHANNEL_OVERWRITE_CREATE(13),
    CHANNEL_OVERWRITE_UPDATE(14),
    CHANNEL_OVERWRITE_DELETE(15),
    MEMBER_KICK(20),
    MEMBER_PRUNE(21),
    MEMBER_BAN_ADD(22),
    MEMBER_BAN_REMOVE(23),
    MEMBER_UPDATE(24),
    MEMBER_ROLE_UPDATE(25),
    MEMBER_MOVE(26),
    MEMBER_DISCONNECT(27),
    BOT_ADD(28),
    ROLE_CREATE(30),
    ROLE_UPDATE(31),
    ROLE_DELETE(32),
    INVITE_CREATE(40),
    INVITE_UPDATE(41),
    INVITE_DELETE(42),
    WEBHOOK_CREATE(50),
    WEBHOOK_UPDATE(51),
    WEBHOOK_DELETE(52),
    EMOJI_CREATE(60),
    EMOJI_UPDATE(61),
    EMOJI_DELETE(62),
    MESSAGE_DELETE(72),
    MESSAGE_BULK_DELETE(73),
    MESSAGE_PIN(74),
    MESSAGE_UNPIN(75),
    INTEGRATION_CREATE(80),
    INTEGRATION_UPDATE(81),
    INTEGRATION_DELETE(82)
}

object AuditLogActionTypeSerializer : KSerializer<AuditLogActionType> {
    override val descriptor: SerialDescriptor = PrimitiveDescriptor("AuditLogActionTypeSerializer", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): AuditLogActionType {
        val target = decoder.decodeInt()
        return AuditLogActionType.values().first {
            it.code == target
        }
    }

    override fun serialize(encoder: Encoder, value: AuditLogActionType) {
        encoder.encodeInt(value.code)
    }
}
