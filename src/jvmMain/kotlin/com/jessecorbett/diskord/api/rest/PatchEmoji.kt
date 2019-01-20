package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class PatchEmoji(
        @JsonProperty("name") val name: String,
        @JsonProperty("roles") val roles: List<String>
)
