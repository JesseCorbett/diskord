package com.jessecorbett.diskord.api.rest.global

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response from the API to get the gateway url.
 *
 * @property url The url to connect to the gateway with.
 */
@Serializable
data class GatewayUrl(val url: String)

/**
 * Response from the API to get the gateway url for bots.
 *
 * @property url The url to connect to the gateway with.
 * @property shards The number of shards discord recommend you use for this bot.
 * @property sessionStartLimit Information on the limits on starting sessions for this bot.
 */
@Serializable
data class GatewayBotUrl(
    val url: String,
    val shards: Int, @SerialName("session_start_limit") val sessionStartLimit: SessionStartLimit
)

/**
 * Information on the limits on starting sessions.
 *
 * @property totalSessions Number of sessions a user is allowed to start.
 * @property remainingSessions Number of sessions a user can still start.
 * @property sessionLimitResetsIn How long until the sessions remaining resets.
 */
@Serializable
data class SessionStartLimit(
    @SerialName("total") val totalSessions: Int,
    @SerialName("remaining") val remainingSessions: Int,
    @SerialName("reset_after") val sessionLimitResetsIn: Int // Passed as int millis
)
