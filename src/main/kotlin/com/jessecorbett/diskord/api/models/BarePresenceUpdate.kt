package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.gateway.commands.UserStatus

data class BarePresenceUpdate(
        @JsonProperty("user") val user: BareUser,
        @JsonProperty("status") val status: UserStatus
)
