package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue


data class AuditLog(
        @JsonProperty("webhooks") val webhooks: List<Webhook>,
        @JsonProperty("users") val users: List<User>,
        @JsonProperty("audit_log_entries") val entries: List<AuditLogEntry>
)

data class AuditLogEntry(
        @JsonProperty("id") val id: String,
        @JsonProperty("target_id") val targetId: String?,
        @JsonProperty("changes") val changes: List<AuditLogChange> = emptyList(),
        @JsonProperty("user_id") val userId: String,
        @JsonProperty("action_type") val actionType: Int,
        @JsonProperty("options") val optionalData: OptionalEntryData?,
        @JsonProperty("reason") val reason: String?
)

// TODO: Make super dynamic and all
data class AuditLogChange(
        @JsonProperty("new_value") val newValue: Any?,
        @JsonProperty("old_value") val oldValue: Any?,
        @JsonProperty("key") val key: String
)

data class OptionalEntryData(
        @JsonProperty("delete_member_days") val pruneKickedAfterDays: String?,
        @JsonProperty("members_removed") val pruneMembersPrunedCount: String?,
        @JsonProperty("channel_id") val deleteChannelId: String?,
        @JsonProperty("count") val deleteMessageCount: String?,
        @JsonProperty("id") val overwriteEntityId: String?,
        @JsonProperty("type") val overwriteEntityType: OverwrittenEntityType?,
        @JsonProperty("role_name") val overwriteRoleName: String?
)

enum class OverwrittenEntityType(@JsonValue val code: String) {
    MEMBER("member"),
    ROLE("role")
}

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
    MESSAGE_DELETE(72)
}
