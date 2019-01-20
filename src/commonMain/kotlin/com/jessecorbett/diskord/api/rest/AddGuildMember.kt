package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class AddGuildMember(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("nick") val nickname: String? = null,
        @JsonProperty("roles") val roleIds: List<String> = emptyList(),
        @JsonProperty("mute") val mute: Boolean = false,
        @JsonProperty("deaf") val deaf: Boolean = false
)
