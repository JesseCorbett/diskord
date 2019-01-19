package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.model.Color

data class CreateGuildRole(
        @JsonProperty("name") val name: String,
        @JsonProperty("permissions") val permissions: Int, // TODO: convert bitwise amounts
        @JsonProperty("color") val color: Color?,
        @JsonProperty("hoist") val displayedSeparately: Boolean,
        @JsonProperty("mentionable") val mentionable: Boolean
)