package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageApplication(
        @JsonProperty("id") val id: String,
        @JsonProperty("cover_image") val coverImage: String,
        @JsonProperty("description") val description: String,
        @JsonProperty("icon") val icon: String,
        @JsonProperty("name") val name: String
)