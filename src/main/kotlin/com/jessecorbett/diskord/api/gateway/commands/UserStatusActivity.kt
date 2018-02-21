package com.jessecorbett.diskord.api.gateway.commands

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import java.time.Instant

data class UserStatusActivity(
        @JsonProperty("name") val name: String,
        @JsonProperty("type") val type: ActivityType,
        @JsonProperty("url") val streamUrl: String?,
        @JsonProperty("timestamps") val timestamps: Timestamps?,
        @JsonProperty("application_id") val applicationId: String?,
        @JsonProperty("details") val details: String?,
        @JsonProperty("state") val partyStatus: String?,
        @JsonProperty("party") val party: ActivityParty?,
        @JsonProperty("assets") val assets: Assets?
)

data class Timestamps(
        @JsonProperty("start") val start: Instant? = null,
        @JsonProperty("end") val end: Instant? = null
)

data class ActivityParty(
        @JsonProperty("id") val id: String?,
        @JsonProperty("size") val size: IntArray? = null
)

data class Assets(
        @JsonProperty("large_image") val largeImage: String?,
        @JsonProperty("large_text") val largeImageText: String?,
        @JsonProperty("small_image") val smallImage: String?,
        @JsonProperty("small_text") val smallImageText: String?
)

enum class ActivityType(@JsonValue val code: Int) {
    GAME(0),
    STREAMING(1),
    LISTENING(2)
}
