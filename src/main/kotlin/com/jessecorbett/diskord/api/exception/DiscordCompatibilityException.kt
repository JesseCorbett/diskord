package com.jessecorbett.diskord.api.exception

/**
 * Thrown when there was some sort of communication issue between discord and diskord.
 */
class DiscordCompatibilityException(override val message: String) : DiscordException()
