package com.jessecorbett.diskord.api.global

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateGroupDM(
    @SerialName("access_tokens") val accessTokens: List<String>,
    @SerialName("nicks") val nicknames: Map<String, String> = emptyMap()   // UserIds to Nicknames
)
