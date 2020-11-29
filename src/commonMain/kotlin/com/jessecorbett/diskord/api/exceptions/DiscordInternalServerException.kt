package com.jessecorbett.diskord.api.exceptions

/**
 * Thrown when there was some sort of error with discord servers.
 *
 * Might be resolved by simply retrying or waiting for Discord to resolve the issue.
 */
public class DiscordInternalServerException : DiscordException()
