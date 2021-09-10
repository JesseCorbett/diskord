package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ThreadMembersUpdate(
    @SerialName("id") val id: String,
    @SerialName("guild_id") val guildId: String,
    @SerialName("member_count") val memberCount: Int,
    @SerialName("added_members") val addedMembers: List<ThreadMember>? = null,
    @SerialName("removed_member_ids") val removedMemberIds: List<String>? = null,
)
