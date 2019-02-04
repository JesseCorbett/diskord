package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Invite(
        @SerialName("code") val code: String,
        @SerialName("guild") val guild: Guild,
        @SerialName("channel") val channel: Channel,
        @SerialName("invite_metadata") val metadata: InviteMetadata? = null
)

@Serializable
data class InviteMetadata(
        @SerialName("inviter") val invitedBy: User,
        @SerialName("uses") val useCount: Int,
        @SerialName("max_uses") val maxUses: Int,
        @SerialName("max_age") val expiresAfterSeconds: Int,
        @SerialName("temporary") val grantsTemporaryMembership: Boolean,
        @SerialName("created_at") val createdAt: String,
        @SerialName("revoked") val isRevoked: Boolean
)
