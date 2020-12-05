package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildMember(
    @SerialName("user") val user: User? = null,
    @SerialName("nick") val nickname: String? = null,
    @SerialName("roles") val roleIds: List<String>,
    @SerialName("joined_at") val joinedAt: String,
    @SerialName("deaf") val isDeaf: Boolean,
    @SerialName("mute") val isMute: Boolean
)
