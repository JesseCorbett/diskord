package com.jessecorbett.diskord.api.websocket.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStatusActivity(
        @SerialName("name") val name: String,
        @SerialName("type") val type: ActivityType,
        @SerialName("url") val streamUrl: String?,
        @SerialName("timestamps") val timestamps: Timestamps?,
        @SerialName("application_id") val applicationId: String?,
        @SerialName("details") val details: String?,
        @SerialName("state") val partyStatus: String?,
        @SerialName("party") val party: ActivityParty?,
        @SerialName("assets") val assets: Assets?
)

@Serializable
data class Timestamps(
        @SerialName("start") val start: String? = null,
        @SerialName("end") val end: String? = null
)

@Serializable
data class ActivityParty(
        @SerialName("id") val id: String?,
        @SerialName("size") val size: List<Int>? = null
)

@Serializable
data class Assets(
        @SerialName("large_image") val largeImage: String?,
        @SerialName("large_text") val largeImageText: String?,
        @SerialName("small_image") val smallImage: String?,
        @SerialName("small_text") val smallImageText: String?
)

enum class ActivityType(val code: Int) {
    GAME(0),
    STREAMING(1),
    LISTENING(2),
    UNKNOWN(3)
}
