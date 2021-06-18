package com.jessecorbett.diskord.api.gateway.commands

import com.jessecorbett.diskord.api.common.UserStatus
import com.jessecorbett.diskord.api.gateway.model.UserStatusActivity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class UpdateStatus(
    @SerialName("since") val idleSince: Int?,
    @SerialName("activities") val activities: List<UserStatusActivity>,
    @SerialName("status") val status: UserStatus,
    @SerialName("afk") val isAfk: Boolean
)
