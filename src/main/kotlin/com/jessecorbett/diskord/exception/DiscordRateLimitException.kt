package com.jessecorbett.diskord.exception

import java.time.Instant

/**
 * Thrown when a client calls an endpoint too many times
 */
class DiscordRateLimitException(val resetsAt: Instant) : DiscordException()
