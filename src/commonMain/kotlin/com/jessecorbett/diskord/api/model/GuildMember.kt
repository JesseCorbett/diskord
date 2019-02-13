package com.jessecorbett.diskord.api.model

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildMember(
        @Optional @SerialName("user") val user: User? = null,
        @Optional @SerialName("nick") val nickname: String? = null,
        @SerialName("roles") val roleIds: List<String>,
        @SerialName("joined_at") val joinedAt: String,
        @SerialName("deaf") val isDeaf: Boolean,
        @SerialName("mute") val isMute: Boolean
)
