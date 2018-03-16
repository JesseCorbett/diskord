package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.Embed

data class CreateMessage(
        @JsonProperty("content") val content: String,
        @JsonProperty("nonce") val nonce: String? = null,
        @JsonProperty("tts") val tts: Boolean = false,
        @JsonProperty("file") val fileContent: ByteArray? = null,
        @JsonProperty("embed") val embed: Embed? = null,
        @JsonProperty("payload_json") val fileUploadEmbed: String? = null
)
