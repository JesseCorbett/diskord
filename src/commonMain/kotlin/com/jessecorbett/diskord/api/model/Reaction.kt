package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reaction(
    @SerialName("count") val count: Int,
    @SerialName("me") val userHasReacted: Boolean,
    @SerialName("emoji") val emoji: Emoji
)
