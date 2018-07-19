package com.jessecorbett.diskord.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Emoji(
        @JsonProperty("id") val id: String?,
        @JsonProperty("name") val name: String,
        @JsonProperty("roles") val whitelistedRoles: List<Role>?,
        @JsonProperty("user") val creator: User?,
        @JsonProperty("require_colons") val requiresWrappingColons: Boolean?,
        @JsonProperty("managed") val isManaged: Boolean?,
        @JsonProperty("animated") val isAnimated: Boolean?
)
