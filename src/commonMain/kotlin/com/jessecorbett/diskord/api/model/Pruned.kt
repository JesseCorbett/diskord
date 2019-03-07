package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pruned(
    @SerialName("pruned") val prunedCount: Int
)
