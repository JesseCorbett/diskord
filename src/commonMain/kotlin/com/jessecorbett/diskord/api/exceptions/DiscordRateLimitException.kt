package com.jessecorbett.diskord.api.exceptions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Thrown when a client calls an endpoint too many times.
 *
 * @property message The error returned by the API.
 * @property retryAt When the rate limit resets in epoch seconds.
 * @property isGlobalRateLimit if the rate limit is API specific or global.
 */
class DiscordRateLimitException(override val message: String, val retryAt: Long, val isGlobalRateLimit: Boolean) :
    DiscordException()

/**
 * Over the wire representation of a [DiscordRateLimitException].
 *
 * @property message The error returned by the API.
 * @property retryAfter When the rate limit resets.
 * @property isGlobal if the rate limit is API specific or global.
 */
@Serializable
data class RateLimitExceeded(
    @SerialName("message") val message: String,
    @SerialName("retry_after") val retryAfter: Long,
    @SerialName("global") val isGlobal: Boolean
)
