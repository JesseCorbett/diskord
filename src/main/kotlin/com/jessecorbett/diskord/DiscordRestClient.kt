package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.GatewayBotUrl
import com.jessecorbett.diskord.api.GatewayUrl
import com.jessecorbett.diskord.api.models.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.api.rest.BulkMessageDelete
import com.jessecorbett.diskord.exception.*
import com.jessecorbett.diskord.internal.httpClient
import com.jessecorbett.diskord.internal.jsonMapper
import okhttp3.*
import java.time.Instant
import kotlin.reflect.KClass

private const val discordApi = "https://discordapp.com/api"

private fun jsonBody(value: Any?): RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonMapper.writeValueAsString(value))

private fun <T: Any> Response.bodyAs(bodyClass: KClass<T>): T = jsonMapper.readValue(this.body()!!.string(), bodyClass.java)

private fun <T: Any> Response.bodyAsListOf(bodyClass: KClass<T>): List<T>
        = jsonMapper.readValue(this.body()!!.string(), jsonMapper.typeFactory.constructCollectionType(List::class.java, bodyClass.java))

class DiscordRestClient(private val token: String) {

    private fun handleFailure(code: Int, headers: Headers) {
        when (code) {
            400 -> throw DiscordBadRequestException()
            401 -> throw DiscordUnauthorizedException()
            403 -> throw DiscordBadPermissionsException()
            404 -> throw DiscordNotFoundException()
            429 -> {
                val resetTime = Instant.ofEpochSecond(headers.get("x-ratelimit-reset")!!.toLong())
                throw DiscordRateLimitException(resetTime)
            }
            502 -> throw DiscordGatewayException()
        }
        if (code in 500..599) {
            throw DiscordInternalServerException()
        }
    }

    private fun commonRequest(url: String): Request.Builder = Request.Builder().url(discordApi + url).header("Authorization", "Bot $token")

    private fun makeRequest(request: Request): Response {
        val response = httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            handleFailure(response.code(), response.headers())
        }
        return response
    }

    private fun getRequest(url: String) = makeRequest(commonRequest(url).get().build())

    private fun postRequest(url: String, body: Any) = makeRequest(commonRequest(url).post(jsonBody(body)).build())

    private fun postRequest(url: String) = makeRequest(commonRequest(url).post(jsonBody(null)).build())

    private fun putRequest(url: String, body: Any) = makeRequest(commonRequest(url).put(jsonBody(body)).build())

    private fun putRequest(url: String) = makeRequest(commonRequest(url).put(jsonBody(null)).build())

    private fun patchRequest(url: String, body: Any) = makeRequest(commonRequest(url).patch(jsonBody(body)).build())

    private fun deleteRequest(url: String) {
        makeRequest(commonRequest(url).delete().build())
    }

    fun getApiGateway() = getRequest("/gateway").bodyAs(GatewayUrl::class)

    fun getBotGateway() = getRequest("/gateway/bot").bodyAs(GatewayBotUrl::class)

    fun getChannel(channelId: String) = getRequest("/channels/$channelId").bodyAs(Channel::class)

    fun updateChannel(channel: Channel) = putRequest("/channels/${channel.id}", channel).bodyAs(Channel::class)

    fun deleteChannel(channelId: String): Unit = deleteRequest("/channels/$channelId")

    fun getChannelMessages(channelId: String) = getRequest("/channels/$channelId/messages").bodyAsListOf(Message::class)

    fun getChannelMessage(channelId: String, messageId: String) = getRequest("/channels/$channelId/messages/$messageId").bodyAs(Message::class)

    fun createMessage(channelId: String, message: CreateMessage) = postRequest("/channels/$channelId/messages", message).bodyAs(Message::class)

    fun addMessageReaction(channelId: String, messageId: String, emoji: String) {
        putRequest("/channels/$channelId/messages/$messageId/reactions/$emoji/@me")
    }

    fun removeMessageReaction(channelId: String, messageId: String, emoji: String, userId: String = "@me") {
        deleteRequest("/channels/$channelId/messages/$messageId/reactions/$emoji/$userId")
    }

    fun getMessageReactions(channelId: String, messageId: String, emoji: String): List<Reaction> = getRequest("/channels/$channelId/messages/$messageId/reaction/$emoji").bodyAsListOf(Reaction::class)

    fun deleteAllMessageReactions(channelId: String, messageId: String) {
        deleteRequest("/channels/$channelId/messages/$messageId/reactions")
    }

    fun editMessage(channelId: String, messageId: String, messageEdit: MessageEdit) = putRequest("/channels/$channelId/messages/$messageId", messageEdit).bodyAs(Message::class)

    fun deleteMessage(channelId: String, messageId: String) {
        deleteRequest("/channels/$channelId/messages/$messageId")
    }

    fun bulkDeleteChannelMessages(channelId: String, bulkMessageDelete: BulkMessageDelete) {
        postRequest("/channels/$channelId/messages/bulk-delete", bulkMessageDelete)
    }

    fun editChannelPermissions(channelId: String, overwrite: Overwrite) {
        putRequest("/channels/$channelId/permissions/${overwrite.id}", overwrite)
    }

    fun getChannelInvites(channelId: String) = getRequest("/channels/$channelId/invites").bodyAsListOf(Invite::class)

    fun createChannelInvite(channelId: String, createInvite: CreateInvite) = postRequest("/channels/$channelId/invites", createInvite).bodyAs(Invite::class)

    fun deleteChannelPermissions(channelId: String, overwriteId: String) {
        deleteRequest("/channels/$channelId/permissions/$overwriteId")
    }

    fun triggerTypingIndicator(channelId: String) {
        postRequest("/channels/$channelId/typing")
    }

    fun getPinnedMessages(channelId: String) = getRequest("/channels/$channelId/pins").bodyAsListOf(Message::class)

    fun pinMessage(channelId: String, messageId: String) {
        putRequest("/channels/$channelId/pins/$messageId")
    }

    fun unpinMessage(channelId: String, messageId: String) {
        putRequest("/channels/$channelId/pins/$messageId")
    }

    fun addGroupDMRecipient(channelId: String, userId: String, groupDMAddRecipient: GroupDMAddRecipient) {
        putRequest("/channels/$channelId/recipients/$userId", groupDMAddRecipient)
    }

    fun removeGroupDMRecipient(channelId: String, userId: String) {
        deleteRequest("/channels/$channelId/recipients/$userId")
    }

    fun getGuildEmoji(guildId: String) = getRequest("/guilds/$guildId/emojis").bodyAsListOf(Emoji::class)

    fun getEmoji(guildId: String, emojiId: String) = getRequest("/guild/$guildId/emojis/$emojiId").bodyAs(Emoji::class)

    fun createEmoji(guildId: String, createEmoji: CreateEmoji) = postRequest("/guild/$guildId/emojis", createEmoji).bodyAs(Emoji::class)

    fun updateEmoji(guildId: String, emojiId: String, emoji: PatchEmoji) = patchRequest("/guild/$guildId/emojis/$emojiId", emoji).bodyAs(Emoji::class)

    fun deleteEmoji(guildId: String, emojiId: String) {
        deleteRequest("/guild/$guildId/emojis/$emojiId")
    }

    fun createGuild(guild: CreateGuild) = postRequest("/guilds", guild).bodyAs(Guild::class)

    fun getGuild(guildId: String) = getRequest("/guilds/$guildId").bodyAs(Guild::class)

    fun updateGuild(guildId: String, guild: PatchGuild) = patchRequest("/guilds/$guildId", guild).bodyAs(Guild::class)

    fun deleteGuild(guildId: String) {
        deleteRequest("/guilds/$guildId")
    }

    fun getGuildChannels(guildId: String) = getRequest("/guilds/$guildId/channels").bodyAsListOf(Channel::class)

    fun createGuildChannel(guildId: String, channel: CreateChannel) = postRequest("/guilds/$guildId/channels", channel).bodyAs(Channel::class)

    fun modifyGuildChannelPositions(guildId: String, positions: List<GuildPosition>) {
        patchRequest("/guilds/$guildId/channels", positions)
    }

    fun getGuildMember(guildId: String, userId: String) = getRequest("/guilds/$guildId/members/$userId").bodyAs(GuildMember::class)

    fun getGuildMembers(guildId: String, limit: Int = 1, afterMember: String = "0") = getRequest("/guilds/$guildId/members?limit=$limit&after=$afterMember").bodyAsListOf(GuildMember::class)

    fun addGuildMember(guildId: String, userId: String, addGuildMember: AddGuildMember) {
        putRequest("/guilds/$guildId/members/$userId", addGuildMember)
    }

    fun updateGuildMember(guildId: String, userId: String, guildMember: PatchGuildMember) {
        patchRequest("/guilds/$guildId/members/$userId", guildMember)
    }

    fun changeGuildMemberNickname(guildId: String, guildMember: PatchGuildMemberNickname) {
        patchRequest("/guilds/$guildId/members/@me/nick", guildMember)
    }

    fun addGuildMemberRole(guildId: String, userId: String, roleId: String) {
        putRequest("/guilds/$guildId/members/$userId/roles/$roleId")
    }

    fun removeGuildMemberRole(guildId: String, userId: String, roleId: String) {
        deleteRequest("/guilds/$guildId/members/$userId/roles/$roleId")
    }

    fun removeGuildMember(guildId: String, userId: String) {
        deleteRequest("/guilds/$guildId/members/$userId")
    }

    fun getGuildBans(guildId: String) = getRequest("/guilds/$guildId/bans").bodyAsListOf(Ban::class)

    fun createGuildBan(guildId: String, userId: String, deleteMessageDays: Int, reason: String) {
        putRequest("/guilds/$guildId/bans/$userId?delete-message-days=$deleteMessageDays&reason=$reason")
    }

    fun removeGuildBan(guildId: String, userId: String) {
        deleteRequest("/guilds/$guildId/bans/$userId")
    }

    fun getGuildRoles(guildId: String) = getRequest("/guilds/$guildId/roles").bodyAsListOf(Role::class)

    fun createGuildRole(guildId: String, guildRole: CreateGuildRole) = postRequest("/guilds/$guildId/roles", guildRole).bodyAs(Role::class)

    fun modifyGuildRolePositions(guildId: String, positions: List<GuildPosition>) {
        patchRequest("/guilds/$guildId/roles", positions)
    }

    fun updateGuildRole(guildId: String, roleId: String, role: PatchRole) = patchRequest("/guilds/$guildId/roles/$roleId", role).bodyAs(Role::class)

    fun deleteGuildRole(guildId: String, roleId: String) {
        deleteRequest("/guilds/$guildId/roles/$roleId")
    }

    fun getPrunePotential(guildId: String, days: Int = 1) = getRequest("/guilds/$guildId/prune?days=$days").bodyAs(Pruned::class)

    fun pruneGuild(guildId: String, days: Int = 1) = postRequest("/guilds/$guildId/prune?days=$days").bodyAs(Pruned::class)

    fun getGuildVoiceRegions(guildId: String) = getRequest("/guilds/$guildId/regions").bodyAsListOf(VoiceRegion::class)

    fun getGuildInvites(guildId: String) = getRequest("/guilds/$guildId/invites").bodyAsListOf(Invite::class)

    fun getGuildIntegrations(guildId: String) = getRequest("/guilds/$guildId/integrations").bodyAsListOf(GuildIntegration::class)

    fun createGuildIntegration(guildId: String, guildIntegration: CreateGuildIntegration) {
        postRequest("/guilds/$guildId/integrations", guildIntegration)
    }

    fun updateGuildIntegration(guildId: String, guildIntegrationId: String, guildIntegration: PatchGuildIntegration) {
        patchRequest("/guilds/$guildId/integrations/$guildIntegrationId", guildIntegration)
    }

    fun deleteGuildIntegration(guildId: String, guildIntegrationId: String) {
        deleteRequest("/guilds/$guildId/integrations/$guildIntegrationId")
    }

    fun syncGuildIntegration(guildId: String, guildIntegrationId: String) {
        postRequest("/guilds/$guildId/integrations/$guildIntegrationId/sync")
    }

    fun getGuildEmbed(guildId: String) = getRequest("/guilds/$guildId/embed").bodyAs(GuildEmbed::class)

    fun updateGuildEmbed(guildId: String, guildEmbed: GuildEmbed) = patchRequest("/guilds/$guildId/embed", guildEmbed).bodyAs(GuildEmbed::class)

    fun getGuildVanityUrl(guildId: String) = getRequest("/guilds/$guildId/vanity-url").bodyAs(Invite::class)

    fun getInvite(inviteCode: String) = getRequest("/invites/$inviteCode").bodyAs(Invite::class)

    fun deleteInvite(inviteCode: String) {
        deleteRequest("/invites/$inviteCode")
    }

    fun getUser(userId: String = "@me") = getRequest("/users/$userId").bodyAs(User::class)

    fun modifyUser(user: ModifyUser) = patchRequest("/users/@me", user).bodyAs(User::class)

    fun getUserGuilds(limit: Int = 100, before: String? = null, after: String? = null): List<Guild> {
        var url = "/users/@me/guilds?limit=$limit"
        if (before != null) {
            url += "&before=$before"
        }
        if (after != null) {
            url += "&after=$after"
        }
        return getRequest(url).bodyAsListOf(Guild::class)
    }

    fun leaveGuild(guildId: String) {
        deleteRequest("/users/@me/guilds/$guildId")
    }

    fun getUserDMs() = getRequest("/users/@me/channels").bodyAsListOf(Channel::class)

    fun createDM(createDM: CreateDM) = postRequest("/users/@me/channels", createDM).bodyAs(Channel::class)

    fun createGroupDM(groupDM: CreateGroupDM) = postRequest("/users/@me/channels", groupDM).bodyAs(Channel::class)

    fun getUserConnections() = getRequest("/users/@me/connections").bodyAsListOf(UserConnection::class)

    fun getVoiceRegions() = getRequest("/voice/regions").bodyAsListOf(VoiceRegion::class)

    fun createWebhook(channelId: String, webhook: CreateWebhook) = postRequest("/channels/$channelId/webhooks", webhook).bodyAs(Webhook::class)

    fun getChannelWebhooks(channelId: String) = getRequest("/channels/$channelId/webhooks").bodyAsListOf(Webhook::class)

    fun getGuildWebhooks(guildId: String) = getRequest("/guilds/$guildId/webhooks").bodyAsListOf(Webhook::class)

    fun getWebhook(webhookId: String) = getRequest("/webhooks/$webhookId").bodyAs(Webhook::class)

    fun getWebhookWithToken(webhookId: String, webhookToken: String) = getRequest("/webhooks/$webhookId/$webhookToken").bodyAs(Webhook::class)

    fun updateWebhook(webhookId: String, webhook: PatchWebhook) = patchRequest("/webhooks/$webhookId", webhook).bodyAs(Webhook::class)

    fun updateWebhookWithToken(webhookId: String, webhookToken: String, webhook: PatchWebhook): Webhook {
        return patchRequest("/webhooks/$webhookId/$webhookToken", webhook).bodyAs(Webhook::class)
    }

    fun deleteWebhook(webhookId: String) {
        deleteRequest("/webhooks/$webhookId")
    }

    fun deleteWebhookWithToken(webhookId: String, webhookToken: String) {
        deleteRequest("/webhooks/$webhookId/$webhookToken")
    }

    fun executeWebhook(webhookId: String, webhookToken: String, webhookSubmission: WebhookSubmission, waitForValidation: Boolean = false) {
        postRequest("/webhooks/$webhookId/$webhookToken?wait=$waitForValidation", webhookSubmission)
    }
}
