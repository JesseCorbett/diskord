package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.BareUser
import com.jessecorbett.diskord.api.common.UserStatus
import com.jessecorbett.diskord.api.gateway.model.UserStatusActivity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PresenceUpdate(
    @SerialName("user") val user: BareUser,
    @SerialName("guild_id") val guildId: String,
    @SerialName("status") val status: String,
    @SerialName("activities") val activities: List<UserStatusActivity>,
    @SerialName("client_status") val clientStatus: ClientStatus
)

@Serializable
public data class ClientStatus(
    @SerialName("desktop") val desktop: String? = null,
    @SerialName("mobile") val mobile: String? = null,
    @SerialName("web") val web: String? = null
)
