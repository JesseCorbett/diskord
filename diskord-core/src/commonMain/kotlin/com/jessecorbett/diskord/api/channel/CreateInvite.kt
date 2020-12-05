package com.jessecorbett.diskord.api.channel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateInvite(
    @SerialName("max_age") val expiresInSeconds: Int = 86400, // 24 hours
    @SerialName("max_uses") val maxUses: Int,
    @SerialName("temporary") val temporaryMembership: Boolean,
    @SerialName("unique") val doNotAttemptReuse: Boolean
)
