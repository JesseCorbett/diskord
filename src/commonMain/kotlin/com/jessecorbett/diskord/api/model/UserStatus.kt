package com.jessecorbett.diskord.api.model

enum class UserStatus(val code: String) {
    ONLINE("online"),
    DO_NOT_DISTURB("dnd"),
    IDLE("idle"),
    INVISIBLE("invisible"),
    OFFLINE("offline")
}
