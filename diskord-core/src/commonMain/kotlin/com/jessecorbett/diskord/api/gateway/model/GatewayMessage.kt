package com.jessecorbett.diskord.api.gateway.model

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
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
public data class GatewayMessage(
    @SerialName("op") val opCode: OpCode,
    @SerialName("d") val dataPayload: JsonElement?,
    @SerialName("s") val sequenceNumber: Int?,
    @SerialName("t") val event: String? // Not using DiscordEvent because there are undocumented events
)

/**
 * The discord gateway event.
 */
@Serializable(with = OpCodeSerializer::class)
public enum class OpCode(public val code: Int) {
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

public object OpCodeSerializer : KSerializer<OpCode> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OpCode", PrimitiveKind.INT)

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: OpCode): Unit = encoder.encodeInt(value.code)

    override fun deserialize(decoder: Decoder): OpCode {
        val code = decoder.decodeInt()
        return OpCode.values().first { it.code == code }
    }
}

