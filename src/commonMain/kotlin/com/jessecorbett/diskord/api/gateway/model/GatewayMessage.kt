package com.jessecorbett.diskord.api.gateway.model

import kotlinx.serialization.*
import kotlinx.serialization.json.JsonElement

/**
 * A message sent through the websocket gateway.
 *
 * @property opCode The code representing the type of message.
 * @property dataPayload The JSON payload, if one is present.
 * @property sequenceNumber The message sequence, used for tracking order of messages.
 * @property event the event name, only for [OpCode.DISPATCH].
 */
@Serializable
data class GatewayMessage(
    @SerialName("op") val opCode: OpCode,
    @SerialName("d") val dataPayload: JsonElement?,
    @SerialName("s") val sequenceNumber: Int? = null,
    @SerialName("t") val event: String? = null // Not using DiscordEvent because there are undocumented events
)

/**
 * The discord gateway event.
 */
@Serializable
enum class OpCode {
    /**
     * A discord event has been sent to the client.
     */
    @SerialName("0") DISPATCH,

    /**
     * Connection heartbeat, requesting a [OpCode.HEARTBEAT_ACK].
     */
    @SerialName("1") HEARTBEAT,

    /**
     * Called after [OpCode.HELLO] is received if this is a new session.
     */
    @SerialName("2") IDENTIFY,

    /**
     * Received when a user presence or status update has occurred.
     */
    @SerialName("3") STATUS_UPDATE,

    /**
     * Received when a user join/leaves/moves voice servers.
     */
    @SerialName("4") VOICE_STATE_UPDATE,

    /**
     * Ping received for a voice server.
     */
    @SerialName("5") VOICE_SERVER_PING,

    /**
     * Called after [OpCode.HELLO] is received if this is an existing session.
     */
    @SerialName("6") RESUME,

    /**
     * The server has requested the client reconnect.
     */
    @SerialName("7") RECONNECT,

    /**
     * Sent to the server to request guild members chunks be sent.
     */
    @SerialName("8") REQUEST_GUILD_MEMBERS,

    /**
     * The gateway session is invalid and should be reestablished.
     */
    @SerialName("9") INVALID_SESSION,

    /**
     * The gateway has acknowledged the connection and the client needs to identify itself.
     */
    @SerialName("10")  HELLO,

    /**
     * Acknowledgement of a [OpCode.HEARTBEAT].
     */
    @SerialName("11") HEARTBEAT_ACK
}
