package com.jessecorbett.diskord.api.common.audit

import com.jessecorbett.diskord.api.common.IntegrationAccount
import com.jessecorbett.diskord.api.common.User
import com.jessecorbett.diskord.api.common.Webhook
import com.jessecorbett.diskord.internal.CodeEnum
import com.jessecorbett.diskord.internal.CodeEnumSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
public enum class OverwrittenEntityType(public override val code: Int): CodeEnum {
    UNKNOWN(-1),
    MEMBER(1),
    ROLE(0)
}

public class OverwrittenEntityTypeSerializer : CodeEnumSerializer<OverwrittenEntityType>(OverwrittenEntityType.UNKNOWN, OverwrittenEntityType.values())

@Serializable
public enum class AuditLogActionType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
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

public class AuditLogActionTypeSerializer : CodeEnumSerializer<AuditLogActionType>(AuditLogActionType.UNKNOWN, AuditLogActionType.values())
