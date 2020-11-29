package com.jessecorbett.diskord.api.exceptions

/**
 * Thrown when there was some sort of IO error communicating with discord.
 *
 * Should be resolved by simply retrying.
 */
public class DiscordGatewayException : DiscordException()
