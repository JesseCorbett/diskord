package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class GroupDMAddRecipient(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("nick") val nickname: String
)
