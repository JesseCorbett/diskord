package com.jessecorbett.diskord.api.gateway.model

import com.jessecorbett.diskord.api.common.Emoji
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
public data class UserStatusActivity(
    @SerialName("name") val name: String,
    @SerialName("type") val type: ActivityType,
    @SerialName("url") val streamUrl: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("timestamps") val timestamps: Timestamps? = null,
    @SerialName("application_id") val applicationId: String? = null,
    @SerialName("details") val details: String? = null,
    @SerialName("state") val partyStatus: String? = null,
    @SerialName("emoji") val emoji: Emoji? = null,
    @SerialName("party") val party: ActivityParty? = null,
    @SerialName("assets") val assets: Assets? = null,
    @SerialName("secrets") val secrets: RichPresenceSecrets? = null,
    @SerialName("instance") val activityIsInstanced: Boolean? = null,
    @SerialName("flags") val activityFlags: Int? = null
)

@Serializable
public data class Timestamps(
    @SerialName("start") val startEpochMilli: Long? = null,
    @SerialName("end") val endEpochMilli: Long? = null
)

@Serializable
public data class ActivityParty(
    @SerialName("id") val id: String? = null,
    @SerialName("size") val size: List<Int>? = null
)

@Serializable
public data class Assets(
    @SerialName("large_image") val largeImage: String? = null,
    @SerialName("large_text") val largeImageText: String? = null,
    @SerialName("small_image") val smallImage: String? = null,
    @SerialName("small_text") val smallImageText: String? = null
)

@Serializable
public data class RichPresenceSecrets(
    @SerialName("join") val joinParty: String? = null,
    @SerialName("spectate") val spectate: String? = null,
    @SerialName("match") val joinInstance: String? = null
)

@Serializable(with = ActivityTypeSerializer::class)
public enum class ActivityType(public val code: Int) {
    UNKNOWN(-1),
    GAME(0),
    STREAMING(1),
    LISTENING(2),
    CUSTOM_STATUS(4),
    COMPETING(5)
}

public object ActivityTypeSerializer : KSerializer<ActivityType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Permissions", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): ActivityType {
        val value = decoder.decodeInt()
        return ActivityType.values().find { it.code == value } ?: ActivityType.UNKNOWN
    }

    override fun serialize(encoder: Encoder, value: ActivityType) {
        encoder.encodeInt(value.code)
    }
}
