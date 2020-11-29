package com.jessecorbett.diskord.api.exceptions

/**
 * Thrown when there was some sort of communication issue between discord and diskord.
 *
 * @property message The specific compatibility issue.
 */
public class DiscordCompatibilityException(override val message: String) : DiscordException()
