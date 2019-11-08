package com.jessecorbett.diskord.api.websocket.model

import com.jessecorbett.diskord.api.model.Emoji
import kotlinx.serialization.*
import kotlinx.serialization.internal.IntDescriptor

@Serializable
data class UserStatusActivity(
    @SerialName("name") val name: String,
    @SerialName("type") val type: ActivityType,
    @SerialName("url") val streamUrl: String? = null,
    @SerialName("timestamps") val timestamps: Timestamps? = null,
    @SerialName("application_id") val applicationId: String? = null,
    @SerialName("details") val details: String? = null,
    @SerialName("state") val partyStatus: String? = null,
    @SerialName("party") val party: ActivityParty? = null,
    @SerialName("assets") val assets: Assets? = null,
    @SerialName("secrets") val secrets: RichPresenceSecrets? = null,
    @SerialName("instance") val activityIsInstanced: Boolean? = null,
    @SerialName("flags") val activityFlags: Int? = null,
    @SerialName("emoji") val emoji: Emoji? = null
)

@Serializable
data class Timestamps(
    @SerialName("start") val start: String? = null,
    @SerialName("end") val end: String? = null
)

@Serializable
data class ActivityParty(
    @SerialName("id") val id: String? = null,
    @SerialName("size") val size: List<Int>? = null
)

@Serializable
data class Assets(
    @SerialName("large_image") val largeImage: String? = null,
    @SerialName("large_text") val largeImageText: String? = null,
    @SerialName("small_image") val smallImage: String? = null,
    @SerialName("small_text") val smallImageText: String? = null
)

@Serializable
data class RichPresenceSecrets(
    @SerialName("join") val joinParty: String? = null,
    @SerialName("spectate") val spectate: String? = null,
    @SerialName("match") val joinInstance: String? = null
)

@Serializable(with = ActivityTypeSerializer::class)
enum class ActivityType(val code: Int) {
    GAME(0),
    STREAMING(1),
    LISTENING(2),
    UNKNOWN(3),
    CUSTOM_STATUS(4)
}

object ActivityTypeSerializer : KSerializer<ActivityType> {
    override val descriptor: SerialDescriptor = IntDescriptor.withName("ActivityTypeSerializer")

    override fun deserialize(decoder: Decoder): ActivityType {
        val target = decoder.decodeInt()
        return ActivityType.values().first {
            it.code == target
        }
    }

    override fun serialize(encoder: Encoder, obj: ActivityType) {
        encoder.encodeInt(obj.code)
    }
}
