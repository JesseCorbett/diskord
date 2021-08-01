package com.jessecorbett.diskord.api.channel

import com.jessecorbett.diskord.api.common.GuildThread
import com.jessecorbett.diskord.api.common.ThreadMember
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ArchivedThreads(
    @SerialName("threads") val threads: List<GuildThread>,
    @SerialName("members") val members: List<ThreadMember>,
    @SerialName("has_more") val hasMore: Boolean,
)
