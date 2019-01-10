package com.jessecorbett.diskord.api.exception

/**
 * Thrown when there was some sort of error with discord servers.
 *
 * Might be resolved by simply retrying or waiting for Discord to resolve the issue.
 */
class DiscordInternalServerException : DiscordException()
