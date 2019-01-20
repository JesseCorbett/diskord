package com.jessecorbett.diskord.api.websocket.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.JsonNode

/**
 * A message sent through the websocket gateway.
 *
 * @property opCode The code representing the type of message.
 * @property dataPayload The JSON payload, if one is present.
 * @property sequenceNumber The message sequence, used for tracking order of messages.
 * @property event the event name, only for [OpCode.DISPATCH].
 */
data class GatewayMessage(
        @JsonProperty("op") val opCode: OpCode,
        @JsonProperty("d") @JsonInclude(JsonInclude.Include.ALWAYS) val dataPayload: JsonNode?,
        @JsonProperty("s") val sequenceNumber: Int?,
        @JsonProperty("t") val event: String? = null
)

/**
 * The discord gateway event.
 *
 * @property code the code represented.
 */
enum class OpCode(@JsonValue val code: Int) {
    /**
     * A discord event has been sent to the client.
     */
    DISPATCH(0),

    /**
     * Connection heartbeat, requesting a [OpCode.HEARTBEAT_ACK].
     */
    HEARTBEAT(1),

    /**
     * Called after [OpCode.HELLO] is received if this is a new session.
     */
    IDENTIFY(2),

    /**
     * Received when a user presence or status update has occurred.
     */
    STATUS_UPDATE(3),

    /**
     * Received when a user join/leaves/moves voice servers.
     */
    VOICE_STATE_UPDATE(4),

    /**
     * Ping received for a voice server.
     */
    VOICE_SERVER_PING(5),

    /**
     * Called after [OpCode.HELLO] is received if this is an existing session.
     */
    RESUME(6),

    /**
     * The server has requested the client reconnect.
     */
    RECONNECT(7),

    /**
     * Sent to the server to request guild members chunks be sent.
     */
    REQUEST_GUILD_MEMBERS(8),

    /**
     * The gateway session is invalid and should be reestablished.
     */
    INVALID_SESSION(9),

    /**
     * The gateway has acknowledged the connection and the client needs to identify itself.
     */
    HELLO(10),

    /**
     * Acknowledgement of a [OpCode.HEARTBEAT].
     */
    HEARTBEAT_ACK(11)
}
