package com.jessecorbett.diskord.api.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageEdit(
        @SerialName("content") val content: String,
        @SerialName("embed") val embed: Embed? = null
)
