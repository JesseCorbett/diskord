package com.jessecorbett.diskord.api.exception

/**
 * Thrown when a request to the REST API was malformed or had bad data.
 *
 * @property message The error returned by the API.
 */
class DiscordBadRequestException(override val message: String?) : DiscordException()
