package com.jessecorbett.diskord.api.websocket.events

import com.jessecorbett.diskord.api.common.BareUser
import com.jessecorbett.diskord.api.common.UserStatus
import com.jessecorbett.diskord.api.websocket.model.UserStatusActivity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PresenceUpdate(
    @SerialName("user") val user: BareUser,
    @SerialName("roles") val roleIds: List<String>,
    @SerialName("game") val activity: UserStatusActivity?,
    @SerialName("guild_id") val guildId: String,
    @SerialName("status") val status: UserStatus,
    @SerialName("activities") val activities: List<UserStatusActivity>
)
