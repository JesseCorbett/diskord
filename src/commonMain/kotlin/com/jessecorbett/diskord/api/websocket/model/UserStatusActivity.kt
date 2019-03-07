package com.jessecorbett.diskord.api.websocket.model

import kotlinx.serialization.*
import kotlinx.serialization.internal.IntDescriptor

@Serializable
data class UserStatusActivity(
    @SerialName("name") val name: String,
    @SerialName("type") val type: ActivityType,
    @Optional @SerialName("url") val streamUrl: String? = null,
    @Optional @SerialName("timestamps") val timestamps: Timestamps? = null,
    @Optional @SerialName("application_id") val applicationId: String? = null,
    @Optional @SerialName("details") val details: String? = null,
    @Optional @SerialName("state") val partyStatus: String? = null,
    @Optional @SerialName("party") val party: ActivityParty? = null,
    @Optional @SerialName("assets") val assets: Assets? = null,
    @Optional @SerialName("secrets") val secrets: RichPresenceSecrets? = null,
    @Optional @SerialName("instance") val activityIsInstanced: Boolean? = null,
    @Optional @SerialName("flags") val activityFlags: Int? = null
)

@Serializable
data class Timestamps(
    @Optional @SerialName("start") val start: String? = null,
    @Optional @SerialName("end") val end: String? = null
)

@Serializable
data class ActivityParty(
    @Optional @SerialName("id") val id: String? = null,
    @Optional @SerialName("size") val size: List<Int>? = null
)

@Serializable
data class Assets(
    @Optional @SerialName("large_image") val largeImage: String? = null,
    @Optional @SerialName("large_text") val largeImageText: String? = null,
    @Optional @SerialName("small_image") val smallImage: String? = null,
    @Optional @SerialName("small_text") val smallImageText: String? = null
)

@Serializable
data class RichPresenceSecrets(
    @Optional @SerialName("join") val joinParty: String? = null,
    @Optional @SerialName("spectate") val spectate: String? = null,
    @Optional @SerialName("match") val joinInstance: String? = null
)

@Serializable(with = ActivityTypeSerializer::class)
enum class ActivityType(val code: Int) {
    GAME(0),
    STREAMING(1),
    LISTENING(2),
    UNKNOWN(3)
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
