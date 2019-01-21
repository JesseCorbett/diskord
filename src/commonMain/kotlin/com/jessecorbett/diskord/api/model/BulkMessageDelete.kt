package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BulkMessageDelete(
        @SerialName("ids") val ids: List<String>,
        @SerialName("channel_id") val channelId: String
)
