package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateGuildIntegration(
        @JsonProperty("type") val type: String,
        @JsonProperty("id") val id: String
)