package com.jessecorbett.diskord.api.gateway.commands

import com.fasterxml.jackson.annotation.JsonValue

enum class UserStatus(@JsonValue val code: String) {
    ONLINE("online"),
    DO_NOT_DISTURB("dnd"),
    IDLE("idle"),
    INVISIBLE("invisible"),
    OFFLINE("offline")
}
