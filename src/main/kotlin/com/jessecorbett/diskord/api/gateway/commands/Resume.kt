package com.jessecorbett.diskord.api.gateway.commands

import com.fasterxml.jackson.annotation.JsonProperty

data class Resume(
        @JsonProperty("token") val token: String? = null,
        @JsonProperty("session_id") val sessionId: String,
        @JsonProperty("seq") val sequenceNumber: Int
)
