package com.jessecorbett.diskord.api.gateway

import com.fasterxml.jackson.annotation.JsonValue

enum class WebSocketCloseCode(@JsonValue val code: Int) {
    /**
     * Standard Socket Codes
     * https://tools.ietf.org/html/rfc6455#section-7.4
     *
     * 0-2999 reserved
     * 3000-3999 application/library/framework defined
     * 4000-4999 reserved for private use
      */
    NORMAL_CLOSURE(1000),
    SERVER_GOING_AWAY(1001),
    SERVER_CLOSED_PROTOCOL_ERROR(1002),
    SERVER_CLOSED_BAD_DATA_TYPE(1003),
    RESERVED_UNDEFINED(1004),
    NO_STATUS_CODE_PRESENT_RESERVED(1005),
    CLOSED_ABNORMALLY_RESERVED(1006),
    INCONSISTENT_DATA_TYPE(1007),
    MESSAGE_VIOLATES_POLICY(1008),
    MESSAGE_TOO_BIG(1009),
    SERVER_DOESNT_SUPPORT_EXTENSION(1010),
    SERVER_UNEXPECTED_CONDITION(1011),
    TLS_FAILED_RESERVED(1015),

    /**
     * Discord Socket Codes
     * https://discordapp.com/developers/docs/topics/opcodes-and-status-codes#gateway-gateway-close-event-codes
     */
    UNKNOWN_ERROR(4000),
    UNKNOWN_OP_CODE(4001),
    PAYLOAD_DECODE_ERROR(4002),
    NOT_AUTHENTICATED(4003),
    AUTHENTICATION_FAILED(4004),
    ALREADY_AUTHENTICATED(4005),
    INVALID_SEQUENCE_NUMBER(4007),
    PAYLOAD_RATE_LIMITED(4008),
    SESSION_TIMEOUT(4009),
    INVALID_SHARD(4010),
    SHARDING_REQUIRED(4011)
}
