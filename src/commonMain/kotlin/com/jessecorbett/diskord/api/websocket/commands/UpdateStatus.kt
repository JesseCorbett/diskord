package com.jessecorbett.diskord.api.websocket.commands

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class UpdateStatus(
        @JsonProperty("since") val idleSince: Instant,
        @JsonProperty("game") val activity: UserStatusActivity? = null,
        @JsonProperty("status") val status: UserStatus = UserStatus.ONLINE,
        @JsonProperty("afk") val isAfk: Boolean = false
)
