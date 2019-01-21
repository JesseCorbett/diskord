package com.jessecorbett.diskord.api.exception

/**
 * Thrown when there was some sort of IO error communicating with discord.
 *
 * Should be resolved by simply retrying.
 */
class DiscordGatewayException : DiscordException()
