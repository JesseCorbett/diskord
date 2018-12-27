package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class Invite(
        @JsonProperty("code") val code: String,
        @JsonProperty("guild") val guild: Guild,
        @JsonProperty("channel") val channel: Channel,
        @JsonProperty("invite_metadata") val metadata: InviteMetadata? = null
)

data class InviteMetadata(
        @JsonProperty("inviter") val invitedBy: User,
        @JsonProperty("uses") val useCount: Int,
        @JsonProperty("max_uses") val maxUses: Int,
        @JsonProperty("max_age") val expiresAfterSeconds: Int,
        @JsonProperty("temporary") val grantsTemporaryMembership: Boolean,
        @JsonProperty("created_at") val createdAt: Instant,
        @JsonProperty("revoked") val isRevoked: Boolean
)
