package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Emoji(
        @JsonProperty("id") val id: String?,
        @JsonProperty("name") val name: String,
        @JsonProperty("roles") val roles: Array<Role>?,
        @JsonProperty("user") val createByUser: User?,
        @JsonProperty("require_colons") val requiresColons: Boolean?,
        @JsonProperty("managed") val managed: Boolean?,
        @JsonProperty("animated") val animated: Boolean?
)
