package com.jessecorbett.diskord.exception

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

/**
 * Thrown when a client calls an endpoint too many times
 */
class DiscordRateLimitException(override val message: String, val retryAt: Instant, val isGlobalRateLimit: Boolean) : DiscordException()

data class RateLimitExceeded(
        @JsonProperty("message") val message: String,
        @JsonProperty("retry_after") val retryAfter: Long,
        @JsonProperty("global") val isGlobal: Boolean
)
