package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.*
import com.jessecorbett.diskord.api.models.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.internal.DiscordToken
import com.jessecorbett.diskord.internal.RestClient
import com.jessecorbett.diskord.internal.bodyAs
import com.jessecorbett.diskord.internal.bodyAsList

class GuildClient(token: DiscordToken, val guildId: String) : RestClient(token) {
    suspend fun getEmoji() = getRequest("/guilds/$guildId/emojis").bodyAsList<Emoji>()

    suspend fun getEmoji(emojiId: String) = getRequest("/guild/$guildId/emojis/$emojiId").bodyAs<Emoji>()

    suspend fun createEmoji(createEmoji: CreateEmoji) = postRequest("/guild/$guildId/emojis", createEmoji).bodyAs<Emoji>()

    suspend fun updateEmoji(emojiId: String, emoji: PatchEmoji) = patchRequest("/guild/$guildId/emojis/$emojiId", emoji).bodyAs<Emoji>()

    suspend fun deleteEmoji(emojiId: String) = deleteRequest("/guild/$guildId/emojis/$emojiId").close()

    suspend fun getGuild() = getRequest("/guilds/$guildId").bodyAs<Guild>()

    suspend fun update(guild: PatchGuild) = patchRequest("/guilds/$guildId", guild).bodyAs<Guild>()

    suspend fun delete() = deleteRequest("/guilds/$guildId").close()

    suspend fun getChannels() = getRequest("/guilds/$guildId/channels").bodyAsList<Channel>()

    suspend fun createChannel(channel: CreateChannel) = postRequest("/guilds/$guildId/channels", channel).bodyAs<Channel>()

    suspend fun modifyChannelPositions(positions: List<GuildPosition>) = patchRequest("/guilds/$guildId/channels", positions).close()

    suspend fun getMember(userId: String) = getRequest("/guilds/$guildId/members/$userId").bodyAs<GuildMember>()

    suspend fun getMembers(limit: Int = 1, afterMember: String = "0") = getRequest("/guilds/$guildId/members?limit=$limit&after=$afterMember").bodyAsList<GuildMember>()

    suspend fun addMember(userId: String, addGuildMember: AddGuildMember) = putRequest("/guilds/$guildId/members/$userId", addGuildMember).close()

    suspend fun updateMember(userId: String, guildMember: PatchGuildMember) = patchRequest("/guilds/$guildId/members/$userId", guildMember).close()

    suspend fun changeMemberNickname(guildMember: PatchGuildMemberNickname) = patchRequest("/guilds/$guildId/members/@me/nick", guildMember).close()

    suspend fun addMemberRole(userId: String, roleId: String) = putRequest("/guilds/$guildId/members/$userId/roles/$roleId").close()

    suspend fun removeMemberRole(userId: String, roleId: String) = deleteRequest("/guilds/$guildId/members/$userId/roles/$roleId").close()

    suspend fun removeMember(userId: String) = deleteRequest("/guilds/$guildId/members/$userId").close()

    suspend fun getBans() = getRequest("/guilds/$guildId/bans").bodyAsList<Ban>()

    suspend fun createBan(userId: String, deleteMessageDays: Int, reason: String) = putRequest("/guilds/$guildId/bans/$userId?delete-message-days=$deleteMessageDays&reason=$reason").close()

    suspend fun removeBan(userId: String) = deleteRequest("/guilds/$guildId/bans/$userId").close()

    suspend fun getRoles() = getRequest("/guilds/$guildId/roles").bodyAsList<Role>()

    suspend fun createRole(guildRole: CreateGuildRole) = postRequest("/guilds/$guildId/roles", guildRole).bodyAs<Role>()

    suspend fun modifyRolePositions(positions: List<GuildPosition>) = patchRequest("/guilds/$guildId/roles", positions).close()

    suspend fun updateRole(roleId: String, role: PatchRole) = patchRequest("/guilds/$guildId/roles/$roleId", role).bodyAs<Role>()

    suspend fun deleteRole(roleId: String) = deleteRequest("/guilds/$guildId/roles/$roleId").close()

    suspend fun getPrunePotential(days: Int = 1) = getRequest("/guilds/$guildId/prune?days=$days").bodyAs<Pruned>()

    suspend fun prune(days: Int = 1) = postRequest("/guilds/$guildId/prune?days=$days").bodyAs<Pruned>()

    suspend fun getVoiceRegions() = getRequest("/guilds/$guildId/regions").bodyAsList<VoiceRegion>()

    suspend fun getInvites() = getRequest("/guilds/$guildId/invites").bodyAsList<Invite>()

    suspend fun getIntegrations() = getRequest("/guilds/$guildId/integrations").bodyAsList<GuildIntegration>()

    suspend fun createIntegration(guildIntegration: CreateGuildIntegration) = postRequest("/guilds/$guildId/integrations", guildIntegration).close()

    suspend fun updateIntegration(guildIntegrationId: String, guildIntegration: PatchGuildIntegration) = patchRequest("/guilds/$guildId/integrations/$guildIntegrationId", guildIntegration).close()

    suspend fun deleteIntegration(guildIntegrationId: String) = deleteRequest("/guilds/$guildId/integrations/$guildIntegrationId").close()

    suspend fun syncIntegration(guildIntegrationId: String) = postRequest("/guilds/$guildId/integrations/$guildIntegrationId/sync").close()

    suspend fun getEmbed() = getRequest("/guilds/$guildId/embed").bodyAs<GuildEmbed>()

    suspend fun updateEmbed(guildEmbed: GuildEmbed) = patchRequest("/guilds/$guildId/embed", guildEmbed).bodyAs<GuildEmbed>()

    suspend fun getVanityUrl() = getRequest("/guilds/$guildId/vanity-url").bodyAs<Invite>()

    suspend fun getWebhooks() = getRequest("/guilds/$guildId/webhooks").bodyAsList<Webhook>()

    suspend fun leave() = deleteRequest("/users/@me/guilds/$guildId").close()

    suspend fun getAuditLog() = getRequest("/guilds/$guildId/audit-logs").bodyAs<AuditLog>()
}
