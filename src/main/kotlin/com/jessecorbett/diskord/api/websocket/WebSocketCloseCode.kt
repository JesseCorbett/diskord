package com.jessecorbett.diskord.api.websocket

import com.fasterxml.jackson.annotation.JsonValue

/**
 * Standard and discord specific close codes.
 *
 * @property code the close code represented.
 */
enum class WebSocketCloseCode(@JsonValue val code: Int) {
    /*
     * Standard Socket Codes
     * https://tools.ietf.org/html/rfc6455#section-7.4
     *
     * 0-2999 reserved
     * 3000-3999 application/library/framework defined
     * 4000-4999 reserved for private use
      */
    /**
     * @suppress meanings documented in link.
     */
    NORMAL_CLOSURE(1000),
    /**
     * @suppress meanings documented in link.
     */
    SERVER_GOING_AWAY(1001),
    /**
     * @suppress meanings documented in link.
     */
    SERVER_CLOSED_PROTOCOL_ERROR(1002),
    /**
     * @suppress meanings documented in link.
     */
    SERVER_CLOSED_BAD_DATA_TYPE(1003),
    /**
     * @suppress meanings documented in link.
     */
    RESERVED_UNDEFINED(1004),
    /**
     * @suppress meanings documented in link.
     */
    NO_STATUS_CODE_PRESENT_RESERVED(1005),
    /**
     * @suppress meanings documented in link.
     */
    CLOSED_ABNORMALLY_RESERVED(1006),
    /**
     * @suppress meanings documented in link.
     */
    INCONSISTENT_DATA_TYPE(1007),
    /**
     * @suppress meanings documented in link.
     */
    MESSAGE_VIOLATES_POLICY(1008),
    /**
     * @suppress meanings documented in link.
     */
    MESSAGE_TOO_BIG(1009),
    /**
     * @suppress meanings documented in link.
     */
    SERVER_DOESNT_SUPPORT_EXTENSION(1010),
    /**
     * @suppress meanings documented in link.
     */
    SERVER_UNEXPECTED_CONDITION(1011),
    /**
     * @suppress meanings documented in link.
     */
    TLS_FAILED_RESERVED(1015),

    /*
     * Discord Socket Codes
     * https://discordapp.com/developers/docs/topics/opcodes-and-status-codes#websocket-websocket-close-event-codes
     */
    /**
     * @suppress meanings documented in link.
     */
    UNKNOWN_ERROR(4000),
    /**
     * @suppress meanings documented in link.
     */
    UNKNOWN_OP_CODE(4001),
    /**
     * @suppress meanings documented in link.
     */
    PAYLOAD_DECODE_ERROR(4002),
    /**
     * @suppress meanings documented in link.
     */
    NOT_AUTHENTICATED(4003),
    /**
     * @suppress meanings documented in link.
     */
    AUTHENTICATION_FAILED(4004),
    /**
     * @suppress meanings documented in link.
     */
    ALREADY_AUTHENTICATED(4005),
    /**
     * @suppress meanings documented in link.
     */
    INVALID_SEQUENCE_NUMBER(4007),
    /**
     * @suppress meanings documented in link.
     */
    PAYLOAD_RATE_LIMITED(4008),
    /**
     * @suppress meanings documented in link.
     */
    SESSION_TIMEOUT(4009),
    /**
     * @suppress meanings documented in link.
     */
    INVALID_SHARD(4010),
    /**
     * @suppress meanings documented in link.
     */
    SHARDING_REQUIRED(4011)
}
