package com.jessecorbett.diskord.api.common.audit

import com.jessecorbett.diskord.api.common.IntegrationAccount
import com.jessecorbett.diskord.api.common.User
import com.jessecorbett.diskord.api.common.Webhook
import kotlinx.serialization.*

@Serializable
public data class AuditLog(
    @SerialName("webhooks") val webhooks: List<Webhook>,
    @SerialName("users") val users: List<User>,
    @SerialName("audit_log_entries") val entries: List<AuditLogEntry>,
    @SerialName("integrations") val integrations: List<PartialGuildIntegration>
)

@Serializable
public data class PartialGuildIntegration(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("account") val account: IntegrationAccount
)

@Serializable
public enum class OverwrittenEntityType {
    @SerialName("1") MEMBER,
    @SerialName("0") ROLE
}

@Serializable
public enum class AuditLogActionType {
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
    @SerialName("24") MEMBER_UPDATE,
    @SerialName("25") MEMBER_ROLE_UPDATE,
    @SerialName("26") MEMBER_MOVE,
    @SerialName("27") MEMBER_DISCONNECT,
    @SerialName("28") BOT_ADD,
    @SerialName("30") ROLE_CREATE,
    @SerialName("31") ROLE_UPDATE,
    @SerialName("32") ROLE_DELETE,
    @SerialName("40") INVITE_CREATE,
    @SerialName("41") INVITE_UPDATE,
    @SerialName("42") INVITE_DELETE,
    @SerialName("50") WEBHOOK_CREATE,
    @SerialName("51") WEBHOOK_UPDATE,
    @SerialName("52") WEBHOOK_DELETE,
    @SerialName("60") EMOJI_CREATE,
    @SerialName("61") EMOJI_UPDATE,
    @SerialName("62") EMOJI_DELETE,
    @SerialName("72") MESSAGE_DELETE,
    @SerialName("73") MESSAGE_BULK_DELETE,
    @SerialName("74") MESSAGE_PIN,
    @SerialName("75") MESSAGE_UNPIN,
    @SerialName("80") INTEGRATION_CREATE,
    @SerialName("81") INTEGRATION_UPDATE,
    @SerialName("82") INTEGRATION_DELETE
}
