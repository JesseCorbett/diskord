package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.websocket.commands.UserStatus

data class BarePresenceUpdate(
        @JsonProperty("user") val user: BareUser,
        @JsonProperty("status") val status: UserStatus
)
