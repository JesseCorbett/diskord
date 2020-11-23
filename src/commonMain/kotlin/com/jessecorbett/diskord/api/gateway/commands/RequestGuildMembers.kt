package com.jessecorbett.diskord.api.gateway.commands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestGuildMembers(
    @SerialName("guild_id") val snowflakeId: String,
    @SerialName("query") val namePrefix: String = "",
    @SerialName("limit") val maxResults: Int = 0
)
