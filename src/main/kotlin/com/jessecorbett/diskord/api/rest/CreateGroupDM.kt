package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateGroupDM(
        @JsonProperty("access_tokens") val accessTokens: List<String>,
        @JsonProperty("nicks") val nicknames: Map<String, String>   // UserIds to Nicknames
)
