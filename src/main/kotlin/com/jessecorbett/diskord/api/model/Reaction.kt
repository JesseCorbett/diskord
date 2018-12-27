package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Reaction(
        @JsonProperty("count") val count: Int,
        @JsonProperty("me") val userHasReacted: Boolean,
        @JsonProperty("emoji") val emoji: Emoji
)
