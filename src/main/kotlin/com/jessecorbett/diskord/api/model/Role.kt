package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Role(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("color") val color: Color,
        @JsonProperty("hoist") val isUserListPinned: Boolean,
        @JsonProperty("position") val position: Int,
        @JsonProperty("permissions") val permissions: Int,
        @JsonProperty("manager") val isManagedByIntegration: Boolean,
        @JsonProperty("mentionable") val isMentionable: Boolean
)
