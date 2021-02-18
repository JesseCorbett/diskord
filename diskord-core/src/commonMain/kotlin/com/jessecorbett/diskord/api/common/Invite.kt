package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Invite(
    @SerialName("code") val code: String,
    @SerialName("guild") val guild: Guild? = null,
    @SerialName("channel") val channel: Channel,
    @SerialName("approximate_presence_count") val approximateOnlineMembers: Int? = null,
    @SerialName("approximate_member_count") val approximateMembers: Int? = null,
    @SerialName("invite_metadata") val metadata: InviteMetadata? = null,
    @SerialName("inviter") val invitedBy: User? = null,
    @SerialName("target_user") val targetUser: User? = null,
    @SerialName("target_user_type") val targetUserType: Int? = null // Always 1 for STREAM
)

@Serializable
public data class InviteMetadata(
    @SerialName("uses") val useCount: Int,
    @SerialName("max_uses") val maxUses: Int,
    @SerialName("max_age") val expiresAfterSeconds: Int,
    @SerialName("temporary") val grantsTemporaryMembership: Boolean,
    @SerialName("created_at") val createdAt: String
)
