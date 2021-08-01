package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ThreadListSync(
    @SerialName("guild_id") val guildId: String,
    @SerialName("channel_ids") val channelIds: List<String>? = null,
    @SerialName("threads") val threads: List<GuildThread>,
    @SerialName("members") val members: List<ThreadMember>,
)
