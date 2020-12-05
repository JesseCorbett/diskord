package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BulkMessageDelete(
    @SerialName("ids") val ids: List<String>,
    @SerialName("channel_id") val channelId: String
)
