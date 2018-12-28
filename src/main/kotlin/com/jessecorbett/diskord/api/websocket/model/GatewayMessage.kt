package com.jessecorbett.diskord.api.websocket.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.JsonNode

data class GatewayMessage(
        @JsonProperty("op") val opCode: OpCode,
        @JsonProperty("d") @JsonInclude(JsonInclude.Include.ALWAYS) val dataPayload: JsonNode?,
        @JsonProperty("s") val sequenceNumber: Int?,
        @JsonProperty("t") val event: String? = null
)

enum class OpCode(@JsonValue val code: Int) {
    DISPATCH(0),
    HEARTBEAT(1),
    IDENTIFY(2),
    STATUS_UPDATE(3),
    VOICE_STATE_UPDATE(4),
    VOICE_SERVER_PING(5),
    RESUME(6),
    RECONNECT(7),
    REQUEST_GUILD_MEMBERS(8),
    INVALID_SESSION(9),
    HELLO(10),
    HEARTBEAT_ACK(11)
}
