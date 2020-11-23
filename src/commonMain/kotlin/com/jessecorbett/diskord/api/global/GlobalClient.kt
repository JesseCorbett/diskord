package com.jessecorbett.diskord.api.global

import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.call.*

/**
 * A REST client for a user and their discord-wide content
 *
 * @param client The REST client implementation
 */
@OptIn(DiskordInternals::class)
class GlobalClient(client: RestClient) : RestClient by client {

    /**
     * Get the gateway url from the API.
     *
     * @return The gateway url.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun getApiGateway(): GatewayUrl = GET("/gateway").receive()

    /**
     * Get the bot specific gateway information from the API.
     *
     * @return The gateway url with bot specific data.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun getBotGateway(): GatewayBotUrl = GET("/gateway/bot").receive()

    /**
     * Create a guild.
     *
     * @param guild The guild to create.
     *
     * @return The created guild.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun createGuild(guild: CreateGuild): Guild = POST("/guilds") { body = guild }.receive()

    /**
     * Get an invite.
     *
     * @param inviteCode The invite's code.
     *
     * @return The invite.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun getInvite(inviteCode: String): Invite = GET("/invites", "/$inviteCode").receive()

    /**
     * Delete an invite.
     *
     * @param inviteCode The invite's code.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun deleteInvite(inviteCode: String): Unit = DELETE("/invites", "/$inviteCode").receive()

    /**
     * Get a user.
     *
     * @param userId The user's id. Defaults to the current user.
     *
     * @return The requested user.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun getUser(userId: String = "@me"): User = GET("/users", "/$userId").receive()

    /**
     * Modify the current user.
     *
     * @param user The modifications to make to the user.
     *
     * @return The updated user.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun modifyUser(user: ModifyUser): User = PATCH("/users", "/@me") { body = user }.receive()

    /**
     * Get a list of guilds that the current user is in.
     *
     * @param limit The max number of guilds to return. Defaults to 100.
     * @param before Specifies the query to only return guilds before this one.
     * @param after Specifies the query to only return guilds after this one.
     *
     * @return A list of partial guilds matching the query.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun getGuilds(limit: Int = 100, before: String? = null, after: String? = null): List<PartialGuild> {
        var path = "?limit=$limit"
        if (before != null) {
            path += "&before=$before"
        }
        if (after != null) {
            path += "&after=$after"
        }
        return GET("/users/@me/guilds", path).receive()
    }

    /**
     * Get the DM channels this user is in.
     *
     * @return List of DM channels for the current user.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    @Deprecated("Currently Discord does not return anything from this endpoint. Instead messages in DMs fire the CHANNEL_CREATE event and you can get a specific DM channel using createDM()")
    suspend fun getDMs(): List<Channel> = GET("/users/@me/channels").receive()

    /**
     * Open a DM channel between the current user and another.
     *
     * Does not actually create a channel if existing DMs already exist between these two users.
     *
     * @param createDM The user to create a DM with.
     *
     * @return The DM channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun createDM(createDM: CreateDM): Channel = POST("/users/@me/channels") { body = createDM }.receive()

    /**
     * Open a group DM channel between the current user and others.
     *
     * Requires access tokens instead of the normal DM requirement of a userId and sufficient permissions.
     *
     * @param groupDM The group DM definition.
     *
     * @return The group DM channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun createGroupDM(groupDM: CreateGroupDM): Channel = POST("/users/@me/channels") { body = groupDM }.receive()

    /**
     * Get all connections to a given user.
     *
     * Only relevant to real users, not bots.
     *
     * @return The user's connections.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun getUserConnections(): List<UserConnection> = GET("/users/@me/channels").receive()

    /**
     * Get the voice regions available for creating a guild.
     *
     * @return The list of voice regions.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    suspend fun getVoiceRegions(): List<VoiceRegion> = GET("/voice/regions").receive()
}
