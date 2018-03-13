package com.jessecorbett.diskord.exception

import java.time.Instant

class DiscordRateLimitException(val resetsAt: Instant) : DiscordException()
