package com.jessecorbett.diskord.api.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BulkMessageDelete(
    @SerialName("messages") val messages: List<String>
)
