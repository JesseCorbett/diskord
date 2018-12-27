package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.model.User

data class Ban(
        @JsonProperty("reason") val reason: String? = null,
        @JsonProperty("user") val user: User
)
