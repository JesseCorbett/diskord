package com.jessecorbett.diskord.api.common

import com.jessecorbett.diskord.api.global.PartialGuild
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Template(
    @SerialName("code") val code: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String?,
    @SerialName("usage_count") val timesUsed: Int,
    @SerialName("creator_id") val creatorId: String,
    @SerialName("creator") val creator: User,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("source_guild_id") val sourceGuildId: String,
    @SerialName("serialized_source_guild") val guildSnapshot: PartialGuild,
    @SerialName("id_dirty") val isNotSynchronized: Boolean
)
