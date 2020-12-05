package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class UserStatus(public val code: String) {
    @SerialName("online") ONLINE("online"),
    @SerialName("dnd") DO_NOT_DISTURB("dnd"),
    @SerialName("idle") IDLE("idle"),
    @SerialName("invisible") INVISIBLE("invisible"),
    @SerialName("offline") OFFLINE("offline")
}
