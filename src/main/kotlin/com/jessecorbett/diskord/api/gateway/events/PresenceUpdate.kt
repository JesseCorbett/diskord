package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.gateway.commands.UserStatus
import com.jessecorbett.diskord.api.gateway.commands.UserStatusActivity
import com.jessecorbett.diskord.api.models.BareUser

data class PresenceUpdate(
        @JsonProperty("user") val user: BareUser,
        @JsonProperty("roles") val roleIds: List<String>,
        @JsonProperty("game") val activity: UserStatusActivity?,
        @JsonProperty("guild_id") val guildId: String,
        @JsonProperty("status") val status: UserStatus,
        @JsonProperty("nick") val nickname: String?
)
