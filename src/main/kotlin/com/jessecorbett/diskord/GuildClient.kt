package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.models.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.internal.RestClient
import com.jessecorbett.diskord.internal.bodyAs
import com.jessecorbett.diskord.internal.bodyAsListOf

class GuildClient(token: String, val guildId: String) : RestClient(token) {
    fun getEmoji() = getRequest("/guilds/$guildId/emojis").bodyAsListOf(Emoji::class)

    fun getEmoji(emojiId: String) = getRequest("/guild/$guildId/emojis/$emojiId").bodyAs(Emoji::class)

    fun createEmoji(createEmoji: CreateEmoji) = postRequest("/guild/$guildId/emojis", createEmoji).bodyAs(Emoji::class)

    fun updateEmoji(emojiId: String, emoji: PatchEmoji) = patchRequest("/guild/$guildId/emojis/$emojiId", emoji).bodyAs(Emoji::class)

    fun deleteEmoji(emojiId: String) {
        deleteRequest("/guild/$guildId/emojis/$emojiId")
    }

    fun getGuild() = getRequest("/guilds/$guildId").bodyAs(Guild::class)

    fun update(guild: PatchGuild) = patchRequest("/guilds/$guildId", guild).bodyAs(Guild::class)

    fun delete() {
        deleteRequest("/guilds/$guildId")
    }

    fun getChannels() = getRequest("/guilds/$guildId/channels").bodyAsListOf(Channel::class)

    fun createChannel(channel: CreateChannel) = postRequest("/guilds/$guildId/channels", channel).bodyAs(Channel::class)

    fun modifyChannelPositions(positions: List<GuildPosition>) {
        patchRequest("/guilds/$guildId/channels", positions)
    }

    fun getMember(userId: String) = getRequest("/guilds/$guildId/members/$userId").bodyAs(GuildMember::class)

    fun getMembers(limit: Int = 1, afterMember: String = "0") = getRequest("/guilds/$guildId/members?limit=$limit&after=$afterMember").bodyAsListOf(GuildMember::class)

    fun addMember(userId: String, addGuildMember: AddGuildMember) {
        putRequest("/guilds/$guildId/members/$userId", addGuildMember)
    }

    fun updateMember(userId: String, guildMember: PatchGuildMember) {
        patchRequest("/guilds/$guildId/members/$userId", guildMember)
    }

    fun changeMemberNickname(guildMember: PatchGuildMemberNickname) {
        patchRequest("/guilds/$guildId/members/@me/nick", guildMember)
    }

    fun addMemberRole(userId: String, roleId: String) {
        putRequest("/guilds/$guildId/members/$userId/roles/$roleId")
    }

    fun removeMemberRole(userId: String, roleId: String) {
        deleteRequest("/guilds/$guildId/members/$userId/roles/$roleId")
    }

    fun removeMember(userId: String) {
        deleteRequest("/guilds/$guildId/members/$userId")
    }

    fun getBans() = getRequest("/guilds/$guildId/bans").bodyAsListOf(Ban::class)

    fun createBan(userId: String, deleteMessageDays: Int, reason: String) {
        putRequest("/guilds/$guildId/bans/$userId?delete-message-days=$deleteMessageDays&reason=$reason")
    }

    fun removeBan(userId: String) {
        deleteRequest("/guilds/$guildId/bans/$userId")
    }

    fun getRoles() = getRequest("/guilds/$guildId/roles").bodyAsListOf(Role::class)

    fun createRole(guildRole: CreateGuildRole) = postRequest("/guilds/$guildId/roles", guildRole).bodyAs(Role::class)

    fun modifyRolePositions(positions: List<GuildPosition>) {
        patchRequest("/guilds/$guildId/roles", positions)
    }

    fun updateRole(roleId: String, role: PatchRole) = patchRequest("/guilds/$guildId/roles/$roleId", role).bodyAs(Role::class)

    fun deleteRole(roleId: String) {
        deleteRequest("/guilds/$guildId/roles/$roleId")
    }

    fun getPrunePotential(days: Int = 1) = getRequest("/guilds/$guildId/prune?days=$days").bodyAs(Pruned::class)

    fun prune(days: Int = 1) = postRequest("/guilds/$guildId/prune?days=$days").bodyAs(Pruned::class)

    fun getVoiceRegions() = getRequest("/guilds/$guildId/regions").bodyAsListOf(VoiceRegion::class)

    fun getInvites() = getRequest("/guilds/$guildId/invites").bodyAsListOf(Invite::class)

    fun getIntegrations() = getRequest("/guilds/$guildId/integrations").bodyAsListOf(GuildIntegration::class)

    fun createIntegration(guildIntegration: CreateGuildIntegration) {
        postRequest("/guilds/$guildId/integrations", guildIntegration)
    }

    fun updateIntegration(guildIntegrationId: String, guildIntegration: PatchGuildIntegration) {
        patchRequest("/guilds/$guildId/integrations/$guildIntegrationId", guildIntegration)
    }

    fun deleteIntegration(guildIntegrationId: String) {
        deleteRequest("/guilds/$guildId/integrations/$guildIntegrationId")
    }

    fun syncIntegration(guildIntegrationId: String) {
        postRequest("/guilds/$guildId/integrations/$guildIntegrationId/sync")
    }

    fun getEmbed() = getRequest("/guilds/$guildId/embed").bodyAs(GuildEmbed::class)

    fun updateEmbed(guildEmbed: GuildEmbed) = patchRequest("/guilds/$guildId/embed", guildEmbed).bodyAs(GuildEmbed::class)

    fun getVanityUrl() = getRequest("/guilds/$guildId/vanity-url").bodyAs(Invite::class)

    fun getWebhooks() = getRequest("/guilds/$guildId/webhooks").bodyAsListOf(Webhook::class)

    fun leave() {
        deleteRequest("/users/@me/guilds/$guildId")
    }
}
