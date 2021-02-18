package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Reaction(
    @SerialName("count") val count: Int,
    @SerialName("me") val userHasReacted: Boolean,
    @SerialName("emoji") val emoji: Emoji
)
