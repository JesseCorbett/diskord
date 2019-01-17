package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.util.Color

data class PatchRole(
        @JsonProperty("name") val name: String,
        @JsonProperty("permissions") val permissions: Int, // TODO: convert bitwise stuff
        @JsonProperty("color") val color: Color,
        @JsonProperty("hoist") val displayedSeparately: Boolean,
        @JsonProperty("mentionable") val mentionable: Boolean
)