package com.jessecorbett.diskord.api.exceptions

import com.jessecorbett.diskord.api.common.Channel

/**
 * Thrown when a model is missing data that is required to perform an operation.
 *
 * (e.g. - [Channel] is missing property [Channel.guildId])
 */
class DiscordMissingDataException(override val message: String? = null) : DiscordException()
