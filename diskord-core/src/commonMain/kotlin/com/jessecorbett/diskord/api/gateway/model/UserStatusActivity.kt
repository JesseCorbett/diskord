package com.jessecorbett.diskord.api.gateway.model

import com.jessecorbett.diskord.api.common.Emoji
import kotlinx.serialization.*

@Serializable
public data class UserStatusActivity(
    @SerialName("name") val name: String,
    @SerialName("type") val type: ActivityType,
    @SerialName("url") val streamUrl: String? = null,
    @SerialName("created_at") val createdAt: String,
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

@Serializable
public enum class ActivityType {
    @SerialName("0") GAME,
    @SerialName("1") STREAMING,
    @SerialName("2") LISTENING,
    @SerialName("4") CUSTOM_STATUS,
    @SerialName("5") COMPETING
}
