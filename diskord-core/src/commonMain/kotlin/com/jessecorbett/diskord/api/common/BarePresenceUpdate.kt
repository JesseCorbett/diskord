package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BarePresenceUpdate(
    @SerialName("user") val user: BareUser,
    @SerialName("status") val status: UserStatus
)
