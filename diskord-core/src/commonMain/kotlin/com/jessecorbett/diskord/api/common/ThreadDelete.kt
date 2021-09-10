package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ThreadDelete(
    @SerialName("id") val id: String,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("parent_id") val parentId: String? = null,
    @SerialName("type") val type: ChannelType,
)
