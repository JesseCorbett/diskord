package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

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
        @JsonProperty("temporary") val temporaryMembership: Boolean,
        @JsonProperty("created_at") val createdAt: ZonedDateTime,
        @JsonProperty("revoked") val revoked: Boolean
)
