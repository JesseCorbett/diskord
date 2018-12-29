package com.jessecorbett.diskord.api.websocket.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GatewayUrl(val url: String)

data class GatewayBotUrl(val url: String, val shards: Int, @JsonProperty("session_start_limit") val sessionStartLimit: SessionStartLimit)

data class SessionStartLimit(
        @JsonProperty("total") val totalSessions: Int,
        @JsonProperty("remaining") val remainingSessions: Int,
        @JsonProperty("reset_after") val sessionLimitResetsIn: Int
)
