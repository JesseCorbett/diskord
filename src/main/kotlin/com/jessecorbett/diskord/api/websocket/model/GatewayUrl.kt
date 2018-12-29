package com.jessecorbett.diskord.api.websocket.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Duration

data class GatewayUrl(val url: String)

data class GatewayBotUrl(val url: String, val shards: Int, @JsonProperty("session_start_limit") val sessionStartLimit: SessionStartLimit)

data class SessionStartLimit(
        @JsonProperty("total") val totalSessions: Int,
        @JsonProperty("remaining") val remainingSessions: Int,
        @JsonProperty("reset_after") val sessionLimitResetsIn: Duration // Passed as int millis, jackson assumes ints are millis for Duration
)
