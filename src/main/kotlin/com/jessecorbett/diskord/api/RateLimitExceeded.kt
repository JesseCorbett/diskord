package com.jessecorbett.diskord.api

import com.fasterxml.jackson.annotation.JsonProperty

data class RateLimitExceeded(
        @JsonProperty("message") val message: String,
        @JsonProperty("retry_after") val retryAfter: Long,
        @JsonProperty("global") val isGlobal: Boolean
)