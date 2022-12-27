package com.jessecorbett.diskord.api.global

import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.defaultJson
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonObject

/**
 * A REST client for a user and their discord-wide content
 *
 * @param client The REST client implementation
 */
@OptIn(DiskordInternals::class)
public class GlobalClient(client: RestClient) : RestClient by client {

    /**
     * Get the gateway url from the API.
     *
     * @return The gateway url.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getApiGateway(): GatewayUrl = GET("/gateway").body()

    /**
     * Get the bot specific gateway information from the API.
     *
     * @return The gateway url with bot specific data.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getBotGateway(): GatewayBotUrl = GET("/gateway/bot").body()

    /**
     * Create a guild.
     *
     * @param guild The guild to create.
     *
     * @return The created guild.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createGuild(guild: CreateGuild): Guild = POST("/guilds") { setBody(guild) }.body()

    /**
     * Get an invite.
     *
     * @param inviteCode The invite's code.
     *
     * @return The invite.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getInvite(inviteCode: String): Invite = GET("/invites", "/$inviteCode").body()

    /**
     * Delete an invite.
     *
     * @param inviteCode The invite's code.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deleteInvite(inviteCode: String): Unit = DELETE("/invites", "/$inviteCode").body()

    /**
     * Get a user.
     *
     * @param userId The user's id. Defaults to the current user.
     *
     * @return The requested user.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getUser(userId: String = "@me"): User = GET("/users", "/$userId").body()

    /**
     * Modify the current user.
     *
     * @param user The modifications to make to the user.
     *
     * @return The updated user.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun modifyUser(user: ModifyUser): User {
        return PATCH("/users", "/@me") { setBody(user) }.body()
    }

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
    public suspend fun getGuilds(limit: Int = 100, before: String? = null, after: String? = null): List<PartialGuild> {
        var path = "?limit=$limit"
        if (before != null) {
            path += "&before=$before"
        }
        if (after != null) {
            path += "&after=$after"
        }
        return GET("/users/@me/guilds", path).body()
    }

    /**
     * Get the DM channels this user is in.
     *
     * @return List of DM channels for the current user.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    @Deprecated("This endpoint no longer works for bots. Instead messages in DMs fire the CHANNEL_CREATE event and you can get a specific DM channel using createDM()")
    public suspend fun getDMs(): List<Channel> = GET("/users/@me/channels").body()

    /**
     * Open a DM channel between the current user and another.
     *
     * Does not create a brand new [Channel] if existing DMs already exist between these two users.
     *
     * Should be used primarily in response to a user action, abuse of this API can lead to a bot being blocked
     * from creating new DMs.
     *
     * @param createDM The user to create a DM with.
     *
     * @return The DM channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createDM(createDM: CreateDM): Channel {
        return POST("/users/@me/channels") { setBody(createDM) }.body()
    }

    /**
     * Get all connections to a given user.
     *
     * Only relevant to real users, not bots.
     *
     * @return The user's connections.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getUserConnections(): List<UserConnection> = GET("/users/@me/channels").body()

    /**
     * Get the voice regions available for creating a guild.
     *
     * @return The list of voice regions.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getVoiceRegions(): List<VoiceRegion> = GET("/voice/regions").body()

    /**
     * Get a list of all sticker packs available to Nitro users.
     *
     * @return The list of sticker packs.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getStickerPacks(): List<StickerPack> = defaultJson.decodeFromJsonElement(
        ListSerializer(StickerPack.serializer()),
        GET("/sticker-packs").body<JsonObject>().getValue("sticker_packs")
    )

    /**
     * Get a specific sticker by sticker ID.
     *
     * @param stickerId the sticker ID
     * @return The sticker.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getSticker(stickerId: String): Sticker = GET("/stickers/$stickerId").body()
}
