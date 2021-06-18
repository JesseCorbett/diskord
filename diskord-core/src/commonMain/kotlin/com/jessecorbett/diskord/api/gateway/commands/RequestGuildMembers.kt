package com.jessecorbett.diskord.api.gateway.commands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class RequestGuildMembers(
    @SerialName("guild_id") val snowflakeId: String,
    @SerialName("query") val namePrefix: String = "",
    @SerialName("limit") val maxResults: Int = 0,
    @SerialName("presences") val includePresences: Boolean = false,
    @SerialName("nonce") val nonce: String? = null
)

// TODO: Include option for user_ids?
