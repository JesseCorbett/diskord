package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class ModifyUser(
        @JsonProperty("username") val username: String,
        @JsonProperty("avatar") val base64AvatarData: String
)
