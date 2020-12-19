package com.jessecorbett.diskord.api.guild

import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.api.common.audit.AuditLog
import com.jessecorbett.diskord.api.common.audit.AuditLogActionType
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.defaultJson
import io.ktor.client.call.*
import kotlinx.serialization.encodeToString

/*
 * Note: Emoji don't follow standard rate limit behavior, and the API responses may not accurately reflect rate limits.
 * Diskord should handle any rate limit blocking automatically, but developers should be aware of this limitation.
 *
 * https://discordapp.com/developers/docs/resources/emoji
 */

/**
 * A REST client for a specific guild and its content
 *
 * @param guildId The id of the guild
 * @param client The REST client implementation
 */
@OptIn(DiskordInternals::class)
public class GuildClient(public val guildId: String, client: RestClient) : RestClient by client {

    /**
     * Get this guild's emoji.
     *
     * @return A list of the guild's custom emoji
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getEmoji(): List<Emoji> = GET("/guilds/$guildId/emojis").receive()

    /**
     * Get a custom emoji.
     *
     * @param emojiId The emoji's id.
     *
     * @return The custom emoji.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getEmoji(emojiId: String): Emoji = GET("/guilds/$guildId/emojis", "/$emojiId").receive()

    /**
     * Create a custom emoji.
     *
     * @param createEmoji The emoji to create.
     *
     * @return The created emoji.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createEmoji(createEmoji: CreateEmoji): Emoji {
        return POST("/guilds/$guildId", "/emojis") { body = createEmoji }.receive()
    }

    /**
     * Update an emoji
     *
     * @param emojiId The emoji to update.
     * @param emoji The updated emoji.
     *
     * @return The updated emoji.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateEmoji(emojiId: String, emoji: PatchEmoji): Emoji {
        return PATCH("/guilds/$guildId/emojis", "/$emojiId") { body = emoji }.receive()
    }

    /**
     * Delete an emoji.
     *
     * @param emojiId The emoji to delete.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deleteEmoji(emojiId: String) {
        DELETE("/guilds/$guildId/emojis", "/$emojiId").receive<Unit>()
    }

    /**
     * Get this guild.
     *
     * @return This guild.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun get(): Guild = GET("/guilds/$guildId").receive()

    /**
     * Update this guild.
     *
     * @param guild The updates to the guild.
     *
     * @return The updated guild.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun update(guild: PatchGuild): Guild {
        return PATCH("/guilds/$guildId") { body = guild }.receive()
    }

    /**
     * Delete this guild. Use with caution, cannot be undone.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun delete(): Unit = DELETE("/guilds/$guildId").receive()

    /**
     * Get this guild's channels.
     *
     * @return The guild's channels.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getChannels(): List<Channel> = GET("/guilds/$guildId/channels").receive()

    /**
     * Create a channel.
     *
     * @param channel The channel to create.
     *
     * @return The created channel.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createChannel(channel: CreateChannel): Channel {
        return POST("/guilds/$guildId/channels") { body = channel }.receive()
    }

    /**
     * Modify the order of channels.
     *
     * @param positions List of channel position descriptors.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun modifyChannelPositions(positions: List<GuildPosition>) {
        PATCH("/guilds/$guildId/channels") { body = positions }.receive<Unit>()
    }

    /**
     * Get a member of this guild.
     *
     * @param userId The id of the member.
     *
     * @return The guild member.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getMember(userId: String): GuildMember {
        return GET("/guilds/$guildId/members", "/$userId").receive()
    }

    /**
     * Get a list of guild members.
     *
     * @param limit Max number of guild members to return.
     * @param afterMember Member id to start querying after.
     *
     * @return List of guild members.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getMembers(limit: Int = 1, afterMember: String = "0"): List<GuildMember> {
        return GET("/guilds/$guildId/members", "?limit=$limit&after=$afterMember").receive()
    }

    /**
     * Add a member to this guild.
     *
     * Requires an OAuth access token.
     *
     * @param userId The user to add.
     * @param addGuildMember Options around adding the user.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun addMember(userId: String, addGuildMember: AddGuildMember) {
        PUT("/guilds/$guildId/members", "/$userId") { body = addGuildMember }.receive<Unit>()
    }

    /**
     * Update a guild member.
     *
     * @param userId The user to update.
     * @param guildMember The updates to the user.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateMember(userId: String, guildMember: PatchGuildMember) {
        PATCH("/guilds/$guildId/members", "/$userId", omitNulls = true) { body = guildMember }.receive<Unit>()
    }

    /**
     * Disconnects a guild member from the voice channel they are currently connected to.
     *
     * @param userId The user to disconnect.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun disconnectMemberVoiceChannel(userId: String) {
        PATCH("/guilds/$guildId/members", "/$userId") {
            body = PatchGuildMemberMoveVoiceChannel(null)
        }.receive<Unit>()
    }

    /**
     * Change a guild member's nickname.
     *
     * @param guildMember The nickname update.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun changeMemberNickname(guildMember: PatchGuildMemberNickname) {
        PATCH("/guilds/$guildId/members/@me/nick") { body = guildMember }.receive<Unit>()
    }

    /**
     * Add a role to a user.
     *
     * @param userId The user to add the role to.
     * @param roleId The role to add.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun addMemberRole(userId: String, roleId: String) {
        PUT("/guilds/$guildId/members", "/$userId/roles/$roleId").receive<Unit>()
    }

    /**
     * Remove a role from a user.
     *
     * @param userId The user to remove the role from.
     * @param roleId The role to remove.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun removeMemberRole(userId: String, roleId: String) {
        DELETE("/guilds/$guildId/members", "/$userId/roles/$roleId").receive<Unit>()
    }

    /**
     * Kick a user from the server.
     *
     * @param userId The user to kick.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun removeMember(userId: String) {
        DELETE("/guilds/$guildId/members", "/$userId").receive<Unit>()
    }

    /**
     * Get user bans.
     *
     * @return The list of bans.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getBans(): List<Ban> = GET("/guilds/$guildId/bans").receive()

    /**
     * Ban a user.
     *
     * @param userId The user to ban.
     * @param deleteMessageDays How many days worth of messages to delete.
     * @param reason Why the user is being banned.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createBan(userId: String, deleteMessageDays: Int, reason: String) {
        createBan(userId, CreateGuildBan(deleteMessageDays, reason))
    }

    /**
     * Ban a user.
     *
     * @param userId The user to ban.
     * @param createGuildBan the guild ban
     *
     * @since 1.7.4
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createBan(userId: String, createGuildBan: CreateGuildBan) {
        PUT("/guilds/$guildId/bans", "/$userId") { body = createGuildBan }.receive<Unit>()
    }

    /**
     * Unban a user.
     *
     * @param userId The user to unban.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun removeBan(userId: String) {
        DELETE("/guilds/$guildId/bans", "/$userId").receive<Unit>()
    }

    /**
     * Get all roles for this guild.
     *
     * @return The list of all roles.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getRoles(): List<Role> = GET("/guilds/$guildId/roles").receive()

    /**
     * Create a role.
     *
     * @param guildRole The role to create.
     *
     * @return The created role.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createRole(guildRole: CreateGuildRole): Role {
        return POST("/guilds/$guildId/roles") { body = guildRole }.receive()
    }

    /**
     * Change role ordering.
     *
     * @param positions List of role position descriptors.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun modifyRolePositions(positions: List<GuildPosition>) {
        PATCH("/guilds/$guildId/roles") { body = positions }.receive<Unit>()
    }

    /**
     * Update a role.
     *
     * @param roleId The role to update.
     * @param role The role updates.
     *
     * @return The updated role.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateRole(roleId: String, role: PatchRole): Role {
        return PATCH("/guilds/$guildId/roles", "/$roleId") { body = role }.receive()
    }

    /**
     * Delete a role.
     *
     * @param roleId The role to delete.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deleteRole(roleId: String) {
        DELETE("/guilds/$guildId/roles", "/$roleId").receive<Unit>()
    }

    /**
     * Get the message prune potential.
     *
     * @param days How many days of pruning to check.
     *
     * @return The potential results.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getPrunePotential(days: Int = 1): Pruned {
        return GET("/guilds/$guildId/prune", "?days=$days").receive()
    }

    /**
     * Prune messages.
     *
     * @param days How many days to prune.
     *
     * @return The pruning results.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun prune(days: Int = 1): Pruned = POST("/guilds/$guildId/prune", "?days=$days").receive()

    /**
     * Get the guild's voice regions.
     *
     * @return The voice regions.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getVoiceRegions(): List<VoiceRegion> = GET("/guilds/$guildId/regions").receive()

    /**
     * Get the guild invites.
     *
     * @return All guild invites.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getInvites(): List<Invite> = GET("/guilds/$guildId/invites").receive()

    /**
     * Get the list of integrations for the guild.
     *
     * @return The list fo guild integrations.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getIntegrations(): List<GuildIntegration> = GET("/guilds/$guildId/integrations").receive()

    /**
     * Create a guild integration.
     *
     * @param guildIntegration The guild integration to create.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun createIntegration(guildIntegration: CreateGuildIntegration) {
        POST("/guilds/$guildId/integrations") { body = guildIntegration }.receive<Unit>()
    }

    /**
     * Update a guild integration.
     *
     * @param guildIntegrationId The integration to update.
     * @param guildIntegration The guild integration update.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateIntegration(guildIntegrationId: String, guildIntegration: PatchGuildIntegration) {
        PATCH("/guilds/$guildId/integrations", "/$guildIntegrationId") { body = guildIntegration }.receive<Unit>()
    }

    /**
     * Delete a guild integration.
     *
     * @param guildIntegrationId The integration to delete.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deleteIntegration(guildIntegrationId: String) {
        DELETE("/guilds/$guildId/integrations", "/$guildIntegrationId").receive<Unit>()
    }

    /**
     * Sync an integration.
     *
     * @param guildIntegrationId The integration to sync.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun syncIntegration(guildIntegrationId: String) {
        POST("/guilds/$guildId/integrations", "/$guildIntegrationId/sync").receive<Unit>()
    }

    /**
     * Get the guild embed.
     *
     * @return The guild embed.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getEmbed(): GuildEmbed = GET("/guilds/$guildId/embed").receive()

    /**
     * Update the guild embed.
     *
     * @param guildEmbed The updates to make.
     *
     * @return The updated guild embed.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun updateEmbed(guildEmbed: GuildEmbed): GuildEmbed {
        return PATCH("/guilds/$guildId/embed") { body = guildEmbed }.receive()
    }

    /**
     * Get the guild's vanity url.
     *
     * @return The vanity url in [Invite] form.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getVanityUrl(): Invite = GET("/guilds/$guildId/vanity-url").receive()

    /**
     * Get the guild's webhooks.
     *
     * @return All the guild's webhooks.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getWebhooks(): List<Webhook> = GET("/guilds/$guildId/webhooks").receive()

    /**
     * Leave the server.
     *
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun leave() {
        DELETE("/users/@me/guilds", "/$guildId").receive<Unit>()
    }

    /**
     * Get the audit log.
     *
     * @return The audit log.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getAuditLog(
        userId: String? = null,
        actionType: AuditLogActionType? = null,
        before: String? = null,
        limit: Int = 50
    ): AuditLog {
        var query = "?limit=$limit"
        if (userId != null) {
            query += "&user_id=$userId"
        }
        if (actionType != null) {
            query += "&action_type=${defaultJson.encodeToString(actionType)}"
        }
        if (before != null) {
            query += "&before=$before"
        }

        return GET("/guilds/$guildId/audit-logs$query", auditLogs = true).receive()
    }
}
