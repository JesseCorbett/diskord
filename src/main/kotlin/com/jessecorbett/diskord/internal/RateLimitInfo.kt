package com.jessecorbett.diskord.internal

import java.time.Instant

data class RateLimitInfo(var limit: Int, var remaining: Int, var resetTime: Instant)