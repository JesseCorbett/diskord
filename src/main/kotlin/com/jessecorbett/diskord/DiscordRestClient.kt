package com.jessecorbett.diskord

import com.fasterxml.jackson.databind.type.CollectionType
import com.jessecorbett.diskord.api.GatewayBotUrl
import com.jessecorbett.diskord.api.GatewayUrl
import com.jessecorbett.diskord.api.models.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.api.rest.BulkMessageDelete
import com.jessecorbett.diskord.internal.httpClient
import com.jessecorbett.diskord.internal.jsonMapper
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import kotlin.reflect.KClass

private const val discordApi = "https://discordapp.com/api"

private val jsonMediaType = MediaType.parse("application/json; charset=utf-8")

class DiscordRestClient(private val token: String) {

    private fun commonRequest(url: String): Request.Builder {
        return Request.Builder().url(discordApi + url).header("Authorization", "Bot $token")
    }

    private fun <T : Any> OkHttpClient.get(url: String, responseClass: KClass<T>): T {
        val request = commonRequest(url).get().build()

        val response = this.newCall(request).execute()

        if (!response.isSuccessful) {
            TODO("Throw exceptions for unsuccessful calls")
        }

        val body = response.body()
        val responseObject = jsonMapper.readValue(body?.string(), responseClass.java)
        body?.close()

        return responseObject
    }

    private fun <T : Any> OkHttpClient.get(url: String, collectionType: CollectionType): T {
        val request = commonRequest(url).get().build()

        val response = this.newCall(request).execute()

        if (!response.isSuccessful) {
            TODO("Throw exceptions for unsuccessful calls")
        }

        val body = response.body()
        val responseObject = jsonMapper.readValue<T>(body?.string(), collectionType)
        body?.close()

        return responseObject
    }

    private fun <T : Any> OkHttpClient.post(url: String, responseClass: KClass<T>, requestBody: Any? = null): T {
        val request = commonRequest(url).post(RequestBody.create(jsonMediaType, jsonMapper.writeValueAsString(requestBody))).build()

        val response = this.newCall(request).execute()

        if (!response.isSuccessful) {
            TODO("Throw exceptions for unsuccessful calls")
        }

        val body = response.body()
        val responseObject = jsonMapper.readValue(body?.string(), responseClass.java)
        body?.close()

        return responseObject
    }

    private fun OkHttpClient.post(url: String, requestBody: Any? = null) {
        val request = commonRequest(url).post(RequestBody.create(jsonMediaType, jsonMapper.writeValueAsString(requestBody))).build()

        val response = this.newCall(request).execute()

        if (!response.isSuccessful) {
            TODO("Throw exceptions for unsuccessful calls")
        }

        response.body()?.close()
    }

    private fun <T : Any> OkHttpClient.put(url: String, responseClass: KClass<T>, requestBody: Any? = null): T {
        val request = commonRequest(url).put(RequestBody.create(jsonMediaType, jsonMapper.writeValueAsString(requestBody))).build()

        val response = this.newCall(request).execute()

        if (!response.isSuccessful) {
            TODO("Throw exceptions for unsuccessful calls")
        }

        val body = response.body()
        val responseObject = jsonMapper.readValue(body?.string(), responseClass.java)
        body?.close()

        return responseObject
    }

    private fun OkHttpClient.put(url: String, requestBody: Any? = null) {
        val request = commonRequest(url).put(RequestBody.create(jsonMediaType, jsonMapper.writeValueAsString(requestBody))).build()

        val response = this.newCall(request).execute()

        if (!response.isSuccessful) {
            TODO("Throw exceptions for unsuccessful calls")
        }

        response.body()?.close()
    }

    private fun <T : Any> OkHttpClient.patch(url: String, responseClass: KClass<T>, requestBody: Any? = null): T {
        val request = commonRequest(url).patch(RequestBody.create(jsonMediaType, jsonMapper.writeValueAsString(requestBody))).build()

        val response = this.newCall(request).execute()

        if (!response.isSuccessful) {
            TODO("Throw exceptions for unsuccessful calls")
        }

        val body = response.body()
        val responseObject = jsonMapper.readValue(body?.string(), responseClass.java)
        body?.close()

        return responseObject
    }

    private fun OkHttpClient.patch(url: String, requestBody: Any? = null) {
        val request = commonRequest(url).patch(RequestBody.create(jsonMediaType, jsonMapper.writeValueAsString(requestBody))).build()

        val response = this.newCall(request).execute()

        if (!response.isSuccessful) {
            TODO("Throw exceptions for unsuccessful calls")
        }

        response.body()?.close()
    }

    private fun OkHttpClient.delete(url: String) {
        val request = commonRequest(url).delete().build()

        val response = this.newCall(request).execute()

        if (!response.isSuccessful) {
            TODO("Throw exceptions for unsuccessful calls")
        }

        response.body()?.close()
    }

    private fun <T : Any> listOfType(listedClass: KClass<T>): CollectionType {
        return jsonMapper.typeFactory.constructCollectionType(List::class.java, listedClass.java)
    }

    fun getGateway(): GatewayUrl {
        return httpClient.get("/gateway", GatewayUrl::class)
    }

    fun getBotGateway(): GatewayBotUrl {
        return httpClient.get("/gateway/bot", GatewayBotUrl::class)
    }



    fun getChannel(channelId: String): Channel {
        return httpClient.get("/channels/$channelId", Channel::class)
    }

    fun updateChannel(channel: Channel): Channel {
        return httpClient.put("/channels/${channel.id}", Channel::class, channel)
    }

    fun deleteChannel(channelId: String) {
        httpClient.delete("/channels/$channelId")
    }

    fun getMessages(channelId: String): List<Message> {
        return httpClient.get("/channels/$channelId/messages", listOfType(Message::class))
    }

    fun getChannelMessage(channelId: String, messageId: String): Message {
        return httpClient.get("/channels/$channelId/messages/$messageId", Message::class)
    }

    fun createMessage(channelId: String, message: CreateMessage): Message {
        return httpClient.post("/channels/$channelId/messages", Message::class, message)
    }

    fun createReaction(channelId: String, messageId: String, emoji: String) {
        httpClient.put("/channels/$channelId/messages/$messageId/reactions/$emoji/@me")
    }

    fun deleteReaction(channelId: String, messageId: String, emoji: String, userId: String = "@me") {
        httpClient.delete("/channels/$channelId/messages/$messageId/reactions/$emoji/$userId")
    }

    fun getReactions(channelId: String, messageId: String, emoji: String): List<Reaction> {
        return httpClient.get("/channels/$channelId/messages/$messageId/reaction/$emoji", listOfType(Reaction::class))
    }

    fun deleteAllReactions(channelId: String, messageId: String) {
        httpClient.delete("/channels/$channelId/messages/$messageId/reactions")
    }

    fun editMessage(channelId: String, messageId: String, messageEdit: MessageEdit): Message {
        return httpClient.put("/channels/$channelId/messages/$messageId", Message::class, messageEdit)
    }

    fun deleteMessage(channelId: String, messageId: String) {
        httpClient.delete("/channels/$channelId/messages/$messageId")
    }

    fun bulkDeleteMessages(channelId: String, bulkMessageDelete: BulkMessageDelete) {
        httpClient.post("/channels/$channelId/messages/bulk-delete", bulkMessageDelete)
    }

    fun editChannelPermissions(channelId: String, overwrite: Overwrite) {
        httpClient.put("/channels/$channelId/permissions/${overwrite.id}", overwrite)
    }

    fun getChannelInvites(channelId: String): List<Invite> {
        return httpClient.get("/channels/$channelId/invites", listOfType(Invite::class))
    }

    fun createChannelInvite(channelId: String, createInvite: CreateInvite): Invite {
        return httpClient.post("/channels/$channelId/invites", Invite::class, createInvite)
    }

    fun deleteChannelPermissions(channelId: String, overwriteId: String) {
        httpClient.delete("/channels/$channelId/permissions/$overwriteId")
    }

    fun triggerTypingIndicator(channelId: String) {
        httpClient.post("/channels/$channelId/typing")
    }

    fun getPinnedMessages(channelId: String): List<Message> {
        return httpClient.get("/channels/$channelId/pins", listOfType(Message::class))
    }

    fun pinMessage(channelId: String, messageId: String) {
        httpClient.put("/channels/$channelId/pins/$messageId")
    }

    fun unpinMessage(channelId: String, messageId: String) {
        httpClient.delete("/channels/$channelId/pins/$messageId")
    }

    fun addGroupDMRecipient(channelId: String, userId: String, groupDMAddRecipient: GroupDMAddRecipient) {
        httpClient.put("/channels/$channelId/recipients/$userId", groupDMAddRecipient)
    }

    fun removeGroupDMRecipient(channelId: String, userId: String) {
        httpClient.delete("/channels/$channelId/recipients/$userId")
    }

    fun getGuildEmoji(guildId: String): List<Emoji> {
        return httpClient.get("/guilds/$guildId/emojis", listOfType(Emoji::class))
    }

    fun getEmoji(guildId: String, emojiId: String): Emoji {
        return httpClient.get("/guild/$guildId/emojis/$emojiId", Emoji::class)
    }

    fun createEmoji(guildId: String, createEmoji: CreateEmoji): Emoji {
        return httpClient.post("/guild/$guildId/emojis", Emoji::class, createEmoji)
    }

    fun updateEmoji(guildId: String, emojiId: String, emoji: PatchEmoji): Emoji {
        return httpClient.patch("/guild/$guildId/emojis/$emojiId", Emoji::class, emoji)
    }

    fun deleteEmoji(guildId: String, emojiId: String) {
        httpClient.delete("/guild/$guildId/emojis/$emojiId")
    }

    fun createGuild(guild: CreateGuild): Guild {
        return httpClient.post("/guilds", Guild::class, guild)
    }

    fun getGuild(guildId: String): Guild {
        return httpClient.get("/guilds/$guildId", Guild::class)
    }

    fun updateGuild(guildId: String, guild: PatchGuild): Guild {
        return httpClient.patch("/guilds/$guildId", Guild::class, guild)
    }

    fun deleteGuild(guildId: String) {
        httpClient.delete("/guilds/$guildId")
    }

    fun getGuildChannels(guildId: String): List<Channel> {
        return httpClient.get("/guilds/$guildId/channels", listOfType(Channel::class))
    }

    fun createGuildChannel(guildId: String, channel: CreateChannel): Channel {
        return httpClient.post("/guilds/$guildId/channels", Channel::class, channel)
    }

    fun modifyGuildChannelPositions(guildId: String, positions: List<GuildPosition>) {
        httpClient.patch("/guilds/$guildId/channels", positions)
    }

    fun getGuildMember(guildId: String, userId: String): GuildMember {
        return httpClient.get("/guilds/$guildId/members/$userId", GuildMember::class)
    }

    fun getGuildMembers(guildId: String, limit: Int = 1, afterMember: String = "0"): List<GuildMember> {
        return httpClient.get("/guilds/$guildId/members?limit=$limit&after=$afterMember", listOfType(GuildMember::class))
    }

    fun addGuildMember(guildId: String, userId: String, addGuildMember: AddGuildMember) {
        httpClient.put("/guilds/$guildId/members/$userId", addGuildMember)
    }

    fun updateGuildMember(guildId: String, userId: String, guildMember: PatchGuildMember) {
        httpClient.patch("/guilds/$guildId/members/$userId", guildMember)
    }

    fun changeGuildMemberNickname(guildId: String, guildMember: PatchGuildMemberNickname) {
        httpClient.patch("/guilds/$guildId/members/@me/nick", guildId)
    }

    fun addGuildMemberRole(guildId: String, userId: String, roleId: String) {
        httpClient.put("/guilds/$guildId/members/$userId/roles/$roleId")
    }

    fun removeGuildMemberRole(guildId: String, userId: String, roleId: String) {
        httpClient.delete("/guilds/$guildId/members/$userId/roles/$roleId")
    }

    fun removeGuildMember(guildId: String, userId: String) {
        httpClient.delete("/guilds/$guildId/members/$userId")
    }

    fun getGuildBans(guildId: String): List<Ban> {
        return httpClient.get("/guilds/$guildId/bans", listOfType(Ban::class))
    }

    fun createGuildBan(guildId: String, userId: String, deleteMessageDays: Int, reason: String = "") {
        httpClient.put("/guilds/$guildId/bans/$userId?delete-message-days=$deleteMessageDays&reason=$reason")
    }

    fun removeGuildBan(guildId: String, userId: String) {
        httpClient.delete("/guilds/$guildId/bans/$userId")
    }

    fun getGuildRoles(guildId: String): List<Role> {
        return httpClient.get("/guilds/$guildId/roles", listOfType(Role::class))
    }

    fun createGuildRole(guildId: String, guildRole: CreateGuildRole): Role {
        return httpClient.post("/guilds/$guildId/roles", Role::class, guildRole)
    }

    fun modifyGuildRolePositions(guildId: String, positions: List<GuildPosition>) {
        httpClient.patch("/guilds/$guildId/roles", positions)
    }

    fun updateGuildRole(guildId: String, roleId: String, role: PatchRole): Role {
        return httpClient.patch("/guilds/$guildId/roles/$roleId", Role::class, role)
    }

    fun deleteGuildRole(guildId: String, roleId: String) {
        httpClient.delete("/guilds/$guildId/roles/$roleId")
    }

    fun getPrunePotential(guildId: String, days: Int = 1): Pruned {
        return httpClient.get("/guilds/$guildId/prune?days=$days", Pruned::class)
    }

    fun pruneGuild(guildId: String, days: Int = 1): Pruned {
        return httpClient.post("/guilds/$guildId/prune?days=$days", Pruned::class)
    }

    fun getGuildVoiceRegions(guildId: String): List<VoiceRegion> {
        return httpClient.get("/guilds/$guildId/regions", listOfType(VoiceRegion::class))
    }

    fun getGuildInvites(guildId: String): List<Invite> {
        return httpClient.get("/guilds/$guildId/invites", listOfType(Invite::class))
    }

    fun getGuildIntegrations(guildId: String): List<GuildIntegration> {
        return httpClient.get("/guilds/$guildId/integrations", listOfType(GuildIntegration::class))
    }

    fun createGuildIntegration(guildId: String, guildIntegration: CreateGuildIntegration) {
        return httpClient.post("/guilds/$guildId/integrations", guildIntegration)
    }

    fun updateGuildIntegration(guildId: String, guildIntegrationId: String, guildIntegration: PatchGuildIntegration) {
        return httpClient.patch("/guilds/$guildId/integrations/$guildIntegrationId", guildIntegration)
    }

    fun deleteGuildIntegration(guildId: String, guildIntegrationId: String) {
        httpClient.delete("/guilds/$guildId/integrations/$guildIntegrationId")
    }

    fun syncGuildIntegration(guildId: String, guildIntegrationId: String) {
        httpClient.post("/guilds/$guildId/integrations/$guildIntegrationId/sync")
    }

    fun getGuildEmbed(guildId: String): GuildEmbed {
        return httpClient.get("/guilds/$guildId/embed", GuildEmbed::class)
    }

    fun updateGuildEmbed(guildId: String, guildEmbed: GuildEmbed): GuildEmbed {
        return httpClient.patch("/guilds/$guildId/embed", GuildEmbed::class, guildEmbed)
    }

    fun getGuildVanityUrl(guildId: String): Invite {
        return httpClient.get("/guilds/$guildId/vanity-url", Invite::class)
    }

    fun getInvite(inviteCode: String): Invite {
        return httpClient.get("/invites/$inviteCode", Invite::class)
    }

    fun deleteInvite(inviteCode: String) {
        httpClient.delete("/invites/$inviteCode")
    }

    fun getUser(userId: String = "@me"): User {
        return httpClient.get("/users/$userId", User::class)
    }

    fun modifyUser(user: ModifyUser): User {
        return httpClient.patch("/users/@me", User::class, user)
    }

    fun getUserGuilds(limit: Int = 100, before: String?, after: String?): List<Guild> {
        var url = "/users/@me/guilds?limit=$limit"
        if (before != null) {
            url += "&before=$before"
        }
        if (after != null) {
            url += "&after=$after"
        }
        return httpClient.get(url, listOfType(Guild::class))
    }

    fun leaveGuild(guildId: String) {
        httpClient.delete("/users/@me/guilds/$guildId")
    }

    fun getUserDMs(): List<Channel> {
        return httpClient.get("/users/@me/channels", listOfType(Channel::class))
    }

    fun createDM(createDM: CreateDM): Channel {
        return httpClient.post("/users/@me/channels", Channel::class, createDM)
    }

    fun createGroupDM(groupDM: CreateGroupDM): Channel {
        return httpClient.post("/users/@me/channels", Channel::class, groupDM)
    }

    fun getUserConnections(): List<UserConnection> {
        return httpClient.get("/users/@me/connections", listOfType(UserConnection::class))
    }

    fun getVoiceRegions(): List<VoiceRegion> {
        return httpClient.get("/voice/regions", listOfType(VoiceRegion::class))
    }

    fun createWebhook(channelId: String, webhook: CreateWebhook): Webhook {
        return httpClient.post("/channels/$channelId/webhooks", Webhook::class, webhook)
    }

    fun getChannelWebhooks(channelId: String): List<Webhook> {
        return httpClient.get("/channels/$channelId/webhooks", listOfType(Webhook::class))
    }

    fun getGuildWebhooks(guildId: String): List<Webhook> {
        return httpClient.get("/guilds/$guildId/webhooks", listOfType(Webhook::class))
    }

    fun getWebhook(webhookId: String): Webhook {
        return httpClient.get("/webhooks/$webhookId", Webhook::class)
    }

    fun getWebhookWithToken(webhookId: String, webhookToken: String): Webhook {
        return httpClient.get("/webhooks/$webhookId/$webhookToken", Webhook::class)
    }

    fun updateWebhook(webhookId: String, webhook: PatchWebhook): Webhook {
        return httpClient.patch("/webhooks/$webhookId", Webhook::class, webhook)
    }

    fun updateWebhookWithToken(webhookId: String, webhookToken: String, webhook: PatchWebhook): Webhook {
        return httpClient.patch("/webhooks/$webhookId/$webhookToken", Webhook::class, webhook)
    }

    fun deleteWebhook(webhookId: String) {
        return httpClient.delete("/webhooks/$webhookId")
    }

    fun deleteWebhookWithToken(webhookId: String, webhookToken: String) {
        return httpClient.delete("/webhooks/$webhookId/$webhookToken")
    }

    fun executeWebhook(webhookId: String, webhookToken: String, webhookSubmission: WebhookSubmission, waitForValidation: Boolean = false) {
        return httpClient.post("/webhooks/$webhookId/$webhookToken?wait=$waitForValidation", webhookSubmission)
    }
}
