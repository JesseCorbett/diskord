package com.jessecorbett.diskord.api.rest.client

import com.jessecorbett.diskord.api.rest.CreateDM
import com.jessecorbett.diskord.api.rest.CreateGroupDM
import com.jessecorbett.diskord.api.rest.CreateGuild
import com.jessecorbett.diskord.api.rest.ModifyUser
import com.jessecorbett.diskord.api.rest.response.PartialGuild
import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.rest.client.internal.RestClient
import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.websocket.model.GatewayBotUrl
import com.jessecorbett.diskord.api.websocket.model.GatewayUrl
import com.jessecorbett.diskord.internal.bodyAs
import com.jessecorbett.diskord.internal.bodyAsList

/**
 * A REST client for a user and their discord-wide content.
 *
 * @param token The user's API token.
 * @param userType The user type, assumed to be a bot.
 */
class DiscordClient(token: String, userType: DiscordUserType = DiscordUserType.BOT) : RestClient(token, userType) {

    /**
     * Get the gateway url from the API.
     *
     * @return The gateway url.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getApiGateway() = getRequest("/gateway").bodyAs<GatewayUrl>()

    /**
     * Get the bot specific gateway information from the API.
     *
     * @return The gateway url with bot specific data.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getBotGateway() = getRequest("/gateway/bot").bodyAs<GatewayBotUrl>()

    /**
     * Create a guild.
     *
     * @param guild The guild to create.
     *
     * @return The created guild.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createGuild(guild: CreateGuild) = postRequest("/guilds", guild).bodyAs<Guild>()

    /**
     * Get an invite.
     *
     * @param inviteCode The invite's code.
     *
     * @return The invite.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getInvite(inviteCode: String) = getRequest("/invites/$inviteCode").bodyAs<Invite>()

    /**
     * Delete an invite.
     *
     * @param inviteCode The invite's code.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun deleteInvite(inviteCode: String) = deleteRequest("/invites/$inviteCode").close()

    /**
     * Get a user.
     *
     * @param userId The user's id. Defaults to the current user.
     *
     * @return The requested user.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getUser(userId: String = "@me") = getRequest("/users/$userId").bodyAs<User>()

    /**
     * Modify the current user.
     *
     * @param user The modifications to make to the user.
     *
     * @return The updated user.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun modifyUser(user: ModifyUser) = patchRequest("/users/@me", user).bodyAs<User>()

    /**
     * Get a list of guilds that the current user is in.
     *
     * @param limit The max number of guilds to return. Defaults to 100.
     * @param before Specifies the query to only return guilds before this one.
     * @param after Specifies the query to only return guilds after this one.
     *
     * @return A list of partial guilds matching the query.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getGuilds(limit: Int = 100, before: String? = null, after: String? = null): List<PartialGuild> {
        var url = "/users/@me/guilds?limit=$limit"
        if (before != null) {
            url += "&before=$before"
        }
        if (after != null) {
            url += "&after=$after"
        }
        return getRequest(url).bodyAsList()
    }

    /**
     * Get the DM channels this user is in.
     *
     * @return List of DM channels for the current user.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    @Deprecated("Currently Discord does not return anything from this endpoint. Instead messages in DMs fire the CHANNEL_CREATE event and you can get a specific DM channel using createDM()")
    suspend fun getDMs() = getRequest("/users/@me/channels").bodyAsList<Channel>()

    /**
     * Open a DM channel between the current user and another.
     *
     * Does not actually create a channel if existing DMs already exist between these two users.
     *
     * @param createDM The user to create a DM with.
     *
     * @return The DM channel.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createDM(createDM: CreateDM) = postRequest("/users/@me/channels", createDM).bodyAs<Channel>()

    /**
     * Open a group DM channel between the current user and others.
     *
     * Requires access tokens instead of the normal DM requirement of a userId and sufficient permissions.
     *
     * @param groupDM The group DM definition.
     *
     * @return The group DM channel.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createGroupDM(groupDM: CreateGroupDM) = postRequest("/users/@me/channels", groupDM).bodyAs<Channel>()

    /**
     * Get all connections to a given user.
     *
     * Only relevant to real users, not bots.
     *
     * @return The user's connections.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getUserConnections() = getRequest("/users/@me/connections").bodyAsList<UserConnection>()

    /**
     * Get the voice regions available for creating a guild.
     *
     * @return The list of voice regions.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getVoiceRegions() = getRequest("/voice/regions").bodyAsList<VoiceRegion>()
}
