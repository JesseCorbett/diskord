package com.jessecorbett.diskord.api.exception

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

/**
 * Thrown when a client calls an endpoint too many times.
 *
 * @property message The error returned by the API.
 * @property retryAt When the rate limit resets.
 * @property isGlobalRateLimit if the rate limit is API specific or global.
 */
class DiscordRateLimitException(override val message: String, val retryAt: Instant, val isGlobalRateLimit: Boolean) : DiscordException()

/**
 * Over the wire representation of a [DiscordRateLimitException].
 *
 * @property message The error returned by the API.
 * @property retryAfter When the rate limit resets.
 * @property isGlobal if the rate limit is API specific or global.
 */
data class RateLimitExceeded(
        @JsonProperty("message") val message: String,
        @JsonProperty("retry_after") val retryAfter: Long,
        @JsonProperty("global") val isGlobal: Boolean
)
