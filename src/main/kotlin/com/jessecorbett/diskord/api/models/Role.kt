package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Role(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("color") val color: Int,
        @JsonProperty("hoist") val isUserListPinned: Boolean,
        @JsonProperty("position") val position: Int,
        @JsonProperty("permissions") val permissions: Int,
        @JsonProperty("manager") val managedByIntegration: Boolean,
        @JsonProperty("mentionable") val mentionable: Boolean
)
