package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class PatchGuildMemberNickname(
        @JsonProperty("nick") val nickname: String
)