package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.models.Embed

data class MessageEdit(
        @JsonProperty("content") val content: String,
        @JsonProperty("embed") val embed: Embed? = null
)
