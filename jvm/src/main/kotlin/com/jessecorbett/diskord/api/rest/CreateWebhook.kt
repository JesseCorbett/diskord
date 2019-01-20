package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateWebhook(
        @JsonProperty("name") val name: String,
        @JsonProperty("avatar") val base64AvatarData: String? = null
)
