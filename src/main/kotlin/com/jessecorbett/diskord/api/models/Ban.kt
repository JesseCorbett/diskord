package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Ban(
        @JsonProperty("reason") val reason: String? = null,
        @JsonProperty("user") val user: User
)