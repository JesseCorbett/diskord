package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BarePresenceUpdate(
        @SerialName("user") val user: BareUser,
        @SerialName("status") val status: UserStatus
)
