package com.jessecorbett.diskord.api.websocket.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Duration

/**
 * Response from the API to get the gateway url.
 *
 * @property url The url to connect to the gateway with.
 */
data class GatewayUrl(val url: String)

/**
 * Response from the API to get the gateway url for bots.
 *
 * @property url The url to connect to the gateway with.
 * @property shards The number of shards discord recommend you use for this bot.
 * @property sessionStartLimit Information on the limits on starting sessions for this bot.
 */
data class GatewayBotUrl(val url: String, val shards: Int, @JsonProperty("session_start_limit") val sessionStartLimit: SessionStartLimit)

/**
 * Information on the limits on starting sessions.
 *
 * @property totalSessions Number of sessions a user is allowed to start.
 * @property remainingSessions Number of sessions a user can still start.
 * @property sessionLimitResetsIn How long until the sessions remaining resets.
 */
data class SessionStartLimit(
        @JsonProperty("total") val totalSessions: Int,
        @JsonProperty("remaining") val remainingSessions: Int,
        @JsonProperty("reset_after") val sessionLimitResetsIn: Duration // Passed as int millis, jackson assumes ints are millis for Duration
)
