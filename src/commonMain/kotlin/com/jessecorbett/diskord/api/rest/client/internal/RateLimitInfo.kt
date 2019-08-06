package com.jessecorbett.diskord.api.rest.client.internal

import com.jessecorbett.diskord.util.DiskordInternals

/**
 * Represents the rate limit status for a client.
 *
 * @property limit The number of requests allowed in this scope.
 * @property remaining The number of requests left until resetTime.
 * @property resetTime When the remaining resets back to limit, in epoch seconds.
 */
@DiskordInternals
data class RateLimitInfo(var limit: Int, var remaining: Int, var resetTime: Long)
