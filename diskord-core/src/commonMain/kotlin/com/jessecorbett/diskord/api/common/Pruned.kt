package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Pruned(
    @SerialName("pruned") val prunedCount: Int
)
