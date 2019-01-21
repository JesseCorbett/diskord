package com.jessecorbett.diskord.api.rest.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PartialGuild(
        @JsonProperty("owner") val userIsOwner: Boolean,
        @JsonProperty("permissions") val permissions: Int,
        @JsonProperty("icon") val iconHash: String?,
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String
)
