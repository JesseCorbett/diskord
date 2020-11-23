package com.jessecorbett.diskord.api.gateway.commands

import com.jessecorbett.diskord.api.common.UserStatus
import com.jessecorbett.diskord.api.gateway.model.UserStatusActivity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateStatus(
    @SerialName("since") val idleSince: Int? = null,
    @SerialName("game") val activity: UserStatusActivity? = null,
    @SerialName("status") val status: UserStatus = UserStatus.ONLINE,
    @SerialName("afk") val isAfk: Boolean = false
)
