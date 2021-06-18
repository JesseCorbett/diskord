package com.jessecorbett.diskord.api.exceptions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A generic discord exception.
 */
public sealed class DiscordException : Exception()

/**
 * Thrown when there was some sort of communication issue between discord and diskord.
 *
 * @property message The specific compatibility issue.
 */
public class DiscordCompatibilityException(override val message: String) : DiscordException()

// HTTP 4XX

/**
 * Thrown when a request to the REST API was malformed or had bad data.
 *
 * @property message The error returned by the API.
 */
public class DiscordBadRequestException(override val message: String?) : DiscordException()

/**
 * Thrown when the token used is not valid or the user does not have permission to access this resource.
 */
public class DiscordUnauthorizedException : DiscordException()

/**
 * Thrown when the resource could not be found or the client uses an incorrect path. Probably the former.
 */
public class DiscordNotFoundException : DiscordException()

/**
 * Thrown when the user does not have permissions for the attempted call.
 */
public class DiscordBadPermissionsException : DiscordException()


/**
 * Thrown when a client calls an endpoint too many times.
 *
 * @property message The error returned by the API.
 * @property retryAfterSeconds When the rate limit resets in seconds.
 * @property isGlobalRateLimit if the rate limit is API specific or global.
 */
public class DiscordRateLimitException(
    override val message: String,
    public val retryAfterSeconds: Long,
    public val isGlobalRateLimit: Boolean
) : DiscordException()

/**
 * Over the wire representation of a [DiscordRateLimitException].
 *
 * @property message The error returned by the API.
 * @property retryAfter When the rate limit resets.
 * @property isGlobal if the rate limit is API specific or global.
 */
@Serializable
public data class RateLimitExceeded(
    @SerialName("message") val message: String,
    @SerialName("retry_after") val retryAfter: Long,
    @SerialName("global") val isGlobal: Boolean
)

// HTTP 5XX

/**
 * Thrown when there was some sort of error with discord servers.
 *
 * Might be resolved by simply retrying or waiting for Discord to resolve the issue.
 */
public class DiscordInternalServerException : DiscordException()

/**
 * Thrown when there was some sort of IO error communicating with discord.
 *
 * Should be resolved by simply retrying.
 */
public class DiscordGatewayException : DiscordException()
