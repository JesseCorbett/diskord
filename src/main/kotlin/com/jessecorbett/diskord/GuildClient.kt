package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.models.*
import com.jessecorbett.diskord.api.rest.*

class GuildClient(token: String, val guildId: String) : RestClient(token) {
    fun getGuildEmoji() = getRequest("/guilds/$guildId/emojis").bodyAsListOf(Emoji::class)

    fun getEmoji(emojiId: String) = getRequest("/guild/$guildId/emojis/$emojiId").bodyAs(Emoji::class)

    fun createEmoji(createEmoji: CreateEmoji) = postRequest("/guild/$guildId/emojis", createEmoji).bodyAs(Emoji::class)

    fun updateEmoji(emojiId: String, emoji: PatchEmoji) = patchRequest("/guild/$guildId/emojis/$emojiId", emoji).bodyAs(Emoji::class)

    fun deleteEmoji(emojiId: String) {
        deleteRequest("/guild/$guildId/emojis/$emojiId")
    }

    fun getGuild() = getRequest("/guilds/$guildId").bodyAs(Guild::class)

    fun updateGuild(guild: PatchGuild) = patchRequest("/guilds/$guildId", guild).bodyAs(Guild::class)

    fun deleteGuild() {
        deleteRequest("/guilds/$guildId")
    }

    fun getGuildChannels() = getRequest("/guilds/$guildId/channels").bodyAsListOf(Channel::class)

    fun createGuildChannel(channel: CreateChannel) = postRequest("/guilds/$guildId/channels", channel).bodyAs(Channel::class)

    fun modifyGuildChannelPositions(positions: List<GuildPosition>) {
        patchRequest("/guilds/$guildId/channels", positions)
    }

    fun getGuildMember(userId: String) = getRequest("/guilds/$guildId/members/$userId").bodyAs(GuildMember::class)

    fun getGuildMembers(limit: Int = 1, afterMember: String = "0") = getRequest("/guilds/$guildId/members?limit=$limit&after=$afterMember").bodyAsListOf(GuildMember::class)

    fun addGuildMember(userId: String, addGuildMember: AddGuildMember) {
        putRequest("/guilds/$guildId/members/$userId", addGuildMember)
    }

    fun updateGuildMember(userId: String, guildMember: PatchGuildMember) {
        patchRequest("/guilds/$guildId/members/$userId", guildMember)
    }

    fun changeGuildMemberNickname(guildMember: PatchGuildMemberNickname) {
        patchRequest("/guilds/$guildId/members/@me/nick", guildMember)
    }

    fun addGuildMemberRole(userId: String, roleId: String) {
        putRequest("/guilds/$guildId/members/$userId/roles/$roleId")
    }

    fun removeGuildMemberRole(userId: String, roleId: String) {
        deleteRequest("/guilds/$guildId/members/$userId/roles/$roleId")
    }

    fun removeGuildMember(userId: String) {
        deleteRequest("/guilds/$guildId/members/$userId")
    }

    fun getGuildBans() = getRequest("/guilds/$guildId/bans").bodyAsListOf(Ban::class)

    fun createGuildBan(userId: String, deleteMessageDays: Int, reason: String) {
        putRequest("/guilds/$guildId/bans/$userId?delete-message-days=$deleteMessageDays&reason=$reason")
    }

    fun removeGuildBan(userId: String) {
        deleteRequest("/guilds/$guildId/bans/$userId")
    }

    fun getGuildRoles() = getRequest("/guilds/$guildId/roles").bodyAsListOf(Role::class)

    fun createGuildRole(guildRole: CreateGuildRole) = postRequest("/guilds/$guildId/roles", guildRole).bodyAs(Role::class)

    fun modifyGuildRolePositions(positions: List<GuildPosition>) {
        patchRequest("/guilds/$guildId/roles", positions)
    }

    fun updateGuildRole(roleId: String, role: PatchRole) = patchRequest("/guilds/$guildId/roles/$roleId", role).bodyAs(Role::class)

    fun deleteGuildRole(roleId: String) {
        deleteRequest("/guilds/$guildId/roles/$roleId")
    }

    fun getPrunePotential(days: Int = 1) = getRequest("/guilds/$guildId/prune?days=$days").bodyAs(Pruned::class)

    fun pruneGuild(days: Int = 1) = postRequest("/guilds/$guildId/prune?days=$days").bodyAs(Pruned::class)

    fun getGuildVoiceRegions() = getRequest("/guilds/$guildId/regions").bodyAsListOf(VoiceRegion::class)

    fun getGuildInvites() = getRequest("/guilds/$guildId/invites").bodyAsListOf(Invite::class)

    fun getGuildIntegrations() = getRequest("/guilds/$guildId/integrations").bodyAsListOf(GuildIntegration::class)

    fun createGuildIntegration(guildIntegration: CreateGuildIntegration) {
        postRequest("/guilds/$guildId/integrations", guildIntegration)
    }

    fun updateGuildIntegration(guildIntegrationId: String, guildIntegration: PatchGuildIntegration) {
        patchRequest("/guilds/$guildId/integrations/$guildIntegrationId", guildIntegration)
    }

    fun deleteGuildIntegration(guildIntegrationId: String) {
        deleteRequest("/guilds/$guildId/integrations/$guildIntegrationId")
    }

    fun syncGuildIntegration(guildIntegrationId: String) {
        postRequest("/guilds/$guildId/integrations/$guildIntegrationId/sync")
    }

    fun getGuildEmbed() = getRequest("/guilds/$guildId/embed").bodyAs(GuildEmbed::class)

    fun updateGuildEmbed(guildEmbed: GuildEmbed) = patchRequest("/guilds/$guildId/embed", guildEmbed).bodyAs(GuildEmbed::class)

    fun getGuildVanityUrl() = getRequest("/guilds/$guildId/vanity-url").bodyAs(Invite::class)

    fun getGuildWebhooks() = getRequest("/guilds/$guildId/webhooks").bodyAsListOf(Webhook::class)

    fun leaveGuild() {
        deleteRequest("/users/@me/guilds/$guildId")
    }
}
