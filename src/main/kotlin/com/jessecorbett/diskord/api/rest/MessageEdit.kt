package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageEdit(
        @JsonProperty("content") val content: String,
        @JsonProperty("embed") val embed: Embed? = null
)
