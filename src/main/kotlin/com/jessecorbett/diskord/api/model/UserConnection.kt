package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserConnection(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("type") val type: String,
        @JsonProperty("revoked") val revoked: Boolean,
        @JsonProperty("integrations") val integrations: List<GuildIntegration> = emptyList()
)
