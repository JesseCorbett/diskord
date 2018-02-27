package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateEmoji(
        @JsonProperty("name") val name: String,
        @JsonProperty("image") val base64imageData: String,
        @JsonProperty("roles") val roleIds: List<String>
)
