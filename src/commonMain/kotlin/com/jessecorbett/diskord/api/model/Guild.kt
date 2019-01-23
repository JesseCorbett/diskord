package com.jessecorbett.diskord.api.model

import kotlinx.serialization.*
import kotlinx.serialization.internal.IntDescriptor

@Serializable
data class Guild(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon") val iconHash: String?,
    @SerialName("splash") val splashHash: String?,
    @Optional @SerialName("owner") val userIsOwner: Boolean? = null,
    @SerialName("owner_id") val ownerId: String,
    @Optional @SerialName("permissions") val permissions: Int? = null,
    @SerialName("region") val region: String,
    @SerialName("afk_channel_id") val afkChannelId: String?,
    @SerialName("afk_timeout") val afkTimeoutSeconds: Int,
    @Optional @SerialName("embed_enabled") val embedEnabled: Boolean? = null,
    @Optional @SerialName("embed_channel_id") val embeddedChannelId: String? = null,
    @SerialName("verification_level") val verificationLevel: VerificationLevel,
    @SerialName("default_message_notifications") val defaultMessageNotificationLevel: NotificationsLevel,
    @SerialName("explicit_content_filter") val explicitContentFilterLevel: ExplicitContentFilterLevel,
    @SerialName("roles") val roles: List<Role>,
    @SerialName("emojis") val emojis: List<Emoji>,
    @SerialName("features") val features: List<String>,
    @SerialName("mfa_level") val mfaLevel: MFALevel,
    @SerialName("application_id") val owningApplicationId: String?,
    @Optional @SerialName("widget_enabled") val widgetEnabled: Boolean? = null,
    @Optional @SerialName("widget_channel_id") val widgetChannelId: String? = null,
    @SerialName("system_channel_id") val systemMessageChannelId: String?
)

@Serializable(with = VerificationLevelSerializer::class)
enum class VerificationLevel(val level: Int) {
    NONE(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4)
}

object VerificationLevelSerializer : KSerializer<VerificationLevel> {
    override val descriptor: SerialDescriptor = IntDescriptor.withName("VerificationLevelSerializer")

    override fun deserialize(input: Decoder): VerificationLevel {
        val target = input.decodeInt()
        return VerificationLevel.values().first {
            it.level == target
        }
    }

    override fun serialize(output: Encoder, obj: VerificationLevel) {
        output.encodeInt(obj.level)
    }
}

@Serializable(with = NotificationsLevelSerializer::class)
enum class NotificationsLevel(val level: Int) {
    ALL_MESSAGES(0),
    ONLY_MENTIONS(1)
}

object NotificationsLevelSerializer : KSerializer<NotificationsLevel> {
    override val descriptor: SerialDescriptor = IntDescriptor.withName("NotificationsLevelSerializer")

    override fun deserialize(input: Decoder): NotificationsLevel {
        val target = input.decodeInt()
        return NotificationsLevel.values().first {
            it.level == target
        }
    }

    override fun serialize(output: Encoder, obj: NotificationsLevel) {
        output.encodeInt(obj.level)
    }
}

@Serializable(with = ExplicitContentFilterLevelSerializer::class)
enum class ExplicitContentFilterLevel(val level: Int) {
    DISABLED(0),
    MEMBERS_WITHOUT_ROLES(1),
    ALL_MEMBERS(2)
}

object ExplicitContentFilterLevelSerializer : KSerializer<ExplicitContentFilterLevel> {
    override val descriptor: SerialDescriptor = IntDescriptor.withName("ExplicitContentFilterLevelSerializer")

    override fun deserialize(input: Decoder): ExplicitContentFilterLevel {
        val target = input.decodeInt()
        return ExplicitContentFilterLevel.values().first {
            it.level == target
        }
    }

    override fun serialize(output: Encoder, obj: ExplicitContentFilterLevel) {
        output.encodeInt(obj.level)
    }
}

@Serializable(with = MFALevelSerializer::class)
enum class MFALevel(val level: Int) {
    NONE(0),
    ELEVATED(1)
}

object MFALevelSerializer : KSerializer<MFALevel> {
    override val descriptor: SerialDescriptor = IntDescriptor.withName("MFALevelSerializer")

    override fun deserialize(input: Decoder): MFALevel {
        val target = input.decodeInt()
        return MFALevel.values().first {
            it.level == target
        }
    }

    override fun serialize(output: Encoder, obj: MFALevel) {
        output.encodeInt(obj.level)
    }
}
