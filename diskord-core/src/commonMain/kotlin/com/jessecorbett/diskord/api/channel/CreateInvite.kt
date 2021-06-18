package com.jessecorbett.diskord.api.channel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateInvite(
    @SerialName("max_age") val expiresInSeconds: Int = 86400, // 24 hours
    @SerialName("max_uses") val maxUses: Int, // 0 for unlimited
    @SerialName("temporary") val temporaryMembership: Boolean,
    @SerialName("unique") val doNotAttemptReuse: Boolean,
    @SerialName("target_user") val targetUserId: String? = null,
    @SerialName("target_user_type") val targetUserType: Int? = null // Always 1 when used
)
