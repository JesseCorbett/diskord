package com.jessecorbett.diskord.api.rest.client

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.api.rest.client.internal.RestClient
import com.jessecorbett.diskord.internal.bodyAs
import com.jessecorbett.diskord.internal.bodyAsList

/*
 * Note: Emoji don't follow standard rate limit behavior and the API responses may not accurately reflect rate limits.
 * Diskord should handle any rate limit blocking automatically, but developers should be aware of this limitation.
 *
 * https://discordapp.com/developers/docs/resources/emoji
 */

/**
 * A REST client for a a specific guild and it's content.
 *
 * @param token The user's API token.
 * @param guildId The id of the guild.
 * @param userType The user type, assumed to be a bot.
 */
class GuildClient(token: String, val guildId: String, userType: DiscordUserType = DiscordUserType.BOT) : RestClient(token, userType) {

    /**
     * Get this guild's emoji.
     *
     * @return A list of the guild's custom emoji
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getEmoji() = getRequest("/guilds/$guildId/emojis").bodyAsList<Emoji>()

    /**
     * Get a custom emoji.
     *
     * @param emojiId The emoji's id.
     *
     * @return The custom emoji.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getEmoji(emojiId: String) = getRequest("/guilds/$guildId/emojis/$emojiId").bodyAs<Emoji>()

    /**
     * Create a custom emoji.
     *
     * @param createEmoji The emoji to create.
     *
     * @return The created emoji.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createEmoji(createEmoji: CreateEmoji) = postRequest("/guilds/$guildId/emojis", createEmoji).bodyAs<Emoji>()

    /**
     * Update an emoji
     *
     * @param emojiId The emoji to update.
     * @param emoji The updated emoji.
     *
     * @return The updated emoji.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun updateEmoji(emojiId: String, emoji: PatchEmoji) = patchRequest("/guilds/$guildId/emojis/$emojiId", emoji).bodyAs<Emoji>()

    /**
     * Delete an emoji.
     *
     * @param emojiId The emoji to delete.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun deleteEmoji(emojiId: String) = deleteRequest("/guilds/$guildId/emojis/$emojiId").close()

    /**
     * Get this guild.
     *
     * @return This guild.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun get() = getRequest("/guilds/$guildId").bodyAs<Guild>()

    /**
     * Update this guild.
     *
     * @param guild The updates to the guild.
     *
     * @return The updated guild.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun update(guild: PatchGuild) = patchRequest("/guilds/$guildId", guild).bodyAs<Guild>()

    /**
     * Delete this guild. Use with caution, cannot be undone.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun delete() = deleteRequest("/guilds/$guildId").close()

    /**
     * Get this guild's channels.
     *
     * @return The guild's channels.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getChannels() = getRequest("/guilds/$guildId/channels").bodyAsList<Channel>()

    /**
     * Create a channel.
     *
     * @param channel The channel to create.
     *
     * @return The created channel.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createChannel(channel: CreateChannel) = postRequest("/guilds/$guildId/channels", channel).bodyAs<Channel>()

    /**
     * Modify the order of channels.
     *
     * @param positions List of channel position descriptors.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun modifyChannelPositions(positions: List<GuildPosition>) = patchRequest("/guilds/$guildId/channels", positions).close()

    /**
     * Get a member of this guild.
     *
     * @param userId The id of the member.
     *
     * @return The guild member.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getMember(userId: String) = getRequest("/guilds/$guildId/members/$userId").bodyAs<GuildMember>()

    /**
     * Get a list of guild members.
     *
     * @param limit Max number of guild members to return.
     * @param afterMember Member id to start querying after.
     *
     * @return List of guild members.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getMembers(limit: Int = 1, afterMember: String = "0") = getRequest("/guilds/$guildId/members?limit=$limit&after=$afterMember").bodyAsList<GuildMember>()

    /**
     * Add a member to this guild.
     *
     * Requires an OAuth access token.
     *
     * @param userId The user to add.
     * @param addGuildMember Options around adding the user.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun addMember(userId: String, addGuildMember: AddGuildMember) = putRequest("/guilds/$guildId/members/$userId", addGuildMember).close()

    /**
     * Update a guild member.
     *
     * @param userId The user to update.
     * @param guildMember The updates to the user.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun updateMember(userId: String, guildMember: PatchGuildMember) = patchRequest("/guilds/$guildId/members/$userId", guildMember).close()

    /**
     * Change a guild member's nickname.
     *
     * @param guildMember The nickname update.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun changeMemberNickname(guildMember: PatchGuildMemberNickname) = patchRequest("/guilds/$guildId/members/@me/nick", guildMember).close()

    /**
     * Add a role to a user.
     *
     * @param userId The user to add the role to.
     * @param roleId The role to add.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun addMemberRole(userId: String, roleId: String) = putRequest("/guilds/$guildId/members/$userId/roles/$roleId").close()

    /**
     * Remove a role from a user.
     *
     * @param userId The user to remove the role from.
     * @param roleId The role to remove.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun removeMemberRole(userId: String, roleId: String) = deleteRequest("/guilds/$guildId/members/$userId/roles/$roleId").close()

    /**
     * Kick a user from the server.
     *
     * @param userId The user to kick.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun removeMember(userId: String) = deleteRequest("/guilds/$guildId/members/$userId").close()

    /**
     * Get user bans.
     *
     * @return The list of bans.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getBans() = getRequest("/guilds/$guildId/bans").bodyAsList<Ban>()

    /**
     * Ban a user.
     *
     * @param userId The user to ban.
     * @param deleteMessageDays How many days worth of messages to delete.
     * @param reason Why the user is being banned.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createBan(userId: String, deleteMessageDays: Int, reason: String) = putRequest("/guilds/$guildId/bans/$userId?delete-message-days=$deleteMessageDays&reason=$reason").close()

    /**
     * Unban a user.
     *
     * @param userId The user to unban.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun removeBan(userId: String) = deleteRequest("/guilds/$guildId/bans/$userId").close()

    /**
     * Get all roles for this guild.
     *
     * @return The list of all roles.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getRoles() = getRequest("/guilds/$guildId/roles").bodyAsList<Role>()

    /**
     * Create a role.
     *
     * @param guildRole The role to create.
     *
     * @return The created role.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createRole(guildRole: CreateGuildRole) = postRequest("/guilds/$guildId/roles", guildRole).bodyAs<Role>()

    /**
     * Change role ordering.
     *
     * @param positions List of role position descriptors.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun modifyRolePositions(positions: List<GuildPosition>) = patchRequest("/guilds/$guildId/roles", positions).close()

    /**
     * Update a role.
     *
     * @param roleId The role to update.
     * @param role The role updates.
     *
     * @return The updated role.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun updateRole(roleId: String, role: PatchRole) = patchRequest("/guilds/$guildId/roles/$roleId", role).bodyAs<Role>()

    /**
     * Delete a role.
     *
     * @param roleId The role to delete.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun deleteRole(roleId: String) = deleteRequest("/guilds/$guildId/roles/$roleId").close()

    /**
     * Get the message prune potential.
     *
     * @param days How many days of pruning to check.
     *
     * @return The potential results.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getPrunePotential(days: Int = 1) = getRequest("/guilds/$guildId/prune?days=$days").bodyAs<Pruned>()

    /**
     * Prune messages.
     *
     * @param days How many days to prune.
     *
     * @return The pruning results.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun prune(days: Int = 1) = postRequest("/guilds/$guildId/prune?days=$days").bodyAs<Pruned>()

    /**
     * Get the guild's voice regions.
     *
     * @return The voice regions.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getVoiceRegions() = getRequest("/guilds/$guildId/regions").bodyAsList<VoiceRegion>()

    /**
     * Get the guild invites.
     *
     * @return All guild invites.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getInvites() = getRequest("/guilds/$guildId/invites").bodyAsList<Invite>()

    /**
     * Get the list of integrations for the guild.
     *
     * @return The list fo guild integrations.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getIntegrations() = getRequest("/guilds/$guildId/integrations").bodyAsList<GuildIntegration>()

    /**
     * Create a guild integration.
     *
     * @param guildIntegration The guild integration to create.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun createIntegration(guildIntegration: CreateGuildIntegration) = postRequest("/guilds/$guildId/integrations", guildIntegration).close()

    /**
     * Update a guild integration.
     *
     * @param guildIntegrationId The integration to update.
     * @param guildIntegration The guild integration update.
     *
     * @throws DiscordException
     */
    suspend fun updateIntegration(guildIntegrationId: String, guildIntegration: PatchGuildIntegration) = patchRequest("/guilds/$guildId/integrations/$guildIntegrationId", guildIntegration).close()

    /**
     * Delete a guild integration.
     *
     * @param guildIntegrationId The integration to delete.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun deleteIntegration(guildIntegrationId: String) = deleteRequest("/guilds/$guildId/integrations/$guildIntegrationId").close()

    /**
     * Sync an integration.
     *
     * @param guildIntegrationId The integration to sync.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun syncIntegration(guildIntegrationId: String) = postRequest("/guilds/$guildId/integrations/$guildIntegrationId/sync").close()

    /**
     * Get the guild embed.
     *
     * @return The guild embed.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getEmbed() = getRequest("/guilds/$guildId/embed").bodyAs<GuildEmbed>()

    /**
     * Update the guild embed.
     *
     * @param guildEmbed The updates to make.
     *
     * @return The updated guild embed.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun updateEmbed(guildEmbed: GuildEmbed) = patchRequest("/guilds/$guildId/embed", guildEmbed).bodyAs<GuildEmbed>()

    /**
     * Get the guild's vanity url.
     *
     * @return The vanity url in [Invite] form.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getVanityUrl() = getRequest("/guilds/$guildId/vanity-url").bodyAs<Invite>()

    /**
     * Get the guild's webhooks.
     *
     * @return All the guild's webhooks.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getWebhooks() = getRequest("/guilds/$guildId/webhooks").bodyAsList<Webhook>()

    /**
     * Leave the server.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun leave() = deleteRequest("/users/@me/guilds/$guildId").close()

    /**
     * Get the audit log.
     *
     * @return The audit log.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getAuditLog() = getRequest("/guilds/$guildId/audit-logs").bodyAs<AuditLog>()
}
