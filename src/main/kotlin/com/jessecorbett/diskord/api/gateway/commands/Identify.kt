package com.jessecorbett.diskord.api.gateway.commands

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

data class Identify(
        @JsonProperty("token") val token: String,
        @JsonProperty("properties") val properties: IdentifyProperties = IdentifyProperties(),
        @JsonProperty("compress") val canCompress: Boolean = false,
        @JsonProperty("large_threshold") val memberCountThreshold: Int = 50,
        @JsonProperty("shard") @JsonInclude(JsonInclude.Include.NON_NULL) val shard: Array<Int>? = null,
        @JsonProperty("presence") val presence: UpdateStatus? = null
)

data class IdentifyProperties(
        @JsonProperty("\$os") val os: String = System.getProperty("os.name"),
        @JsonProperty("\$broswer") val browser: String = "diskord",
        @JsonProperty("\$device") val device: String = "diskord"
)
