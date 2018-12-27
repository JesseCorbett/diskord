package com.jessecorbett.diskord.api.websocket.commands

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

data class Identify(
        @JsonProperty("token") val token: String,
        @JsonProperty("shard") @JsonInclude(JsonInclude.Include.NON_NULL) val shard: List<Int>? = null,
        @JsonProperty("compress") val canCompress: Boolean = false,
        @JsonProperty("large_threshold") val memberCountThreshold: Int = 50,
        @JsonProperty("presence") val presence: UpdateStatus? = null,
        @JsonProperty("properties") val properties: IdentifyProperties = IdentifyProperties()
)

data class IdentifyProperties(
        @JsonProperty("\$os") val os: String = System.getProperty("os.name"),
        @JsonProperty("\$broswer") val browser: String = "diskord",
        @JsonProperty("\$device") val device: String = "diskord"
)
