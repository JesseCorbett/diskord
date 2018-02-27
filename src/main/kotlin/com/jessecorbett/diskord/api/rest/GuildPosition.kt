package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class GuildPosition(
        @JsonProperty("id") val id: String,
        @JsonProperty("position") val position: Int
)