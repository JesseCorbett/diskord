package com.jessecorbett.diskord.api.websocket.commands

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IdentifyShard(
        @SerialName("token") val token: String,
        @SerialName("shard") val shard: List<Int>,
        @SerialName("compress") val canCompress: Boolean = false,
        @SerialName("large_threshold") val memberCountThreshold: Int = 50,
        @SerialName("presence") val presence: UpdateStatus? = null,
        @SerialName("properties") val properties: IdentifyProperties = IdentifyProperties()
)

@Serializable
data class Identify(
        @SerialName("token") val token: String,
        @SerialName("compress") val canCompress: Boolean = false,
        @SerialName("large_threshold") val memberCountThreshold: Int = 50,
        @SerialName("presence") val presence: UpdateStatus? = null,
        @SerialName("properties") val properties: IdentifyProperties = IdentifyProperties()
)

@Serializable
data class IdentifyProperties(
        @SerialName("\$os") val os: String = "linux", // TODO: Make multiplatform
        @SerialName("\$browser") val browser: String = "diskord",
        @SerialName("\$device") val device: String = "diskord"
)
