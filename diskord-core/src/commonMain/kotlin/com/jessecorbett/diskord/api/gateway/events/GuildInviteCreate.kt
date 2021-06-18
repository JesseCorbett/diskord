package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildInviteCreate(
    @SerialName("channel_id") val channelId: String,
    @SerialName("code") val code: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("inviter") val inviter: User? = null,
    @SerialName("max_age") val validForSeconds: Long,
    @SerialName("target_user") val targetUser: User? = null,
    @SerialName("target_user_type") val targetUserType: Int? = null,
    @SerialName("temporary") val temporary: Boolean,
    @SerialName("uses") val uses: Int
)
