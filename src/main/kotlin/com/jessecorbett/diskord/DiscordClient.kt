package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.GatewayBotUrl
import com.jessecorbett.diskord.api.GatewayUrl
import com.jessecorbett.diskord.api.models.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.api.rest.response.PartialGuild
import com.jessecorbett.diskord.internal.RestClient
import com.jessecorbett.diskord.internal.bodyAs
import com.jessecorbett.diskord.internal.bodyAsListOf

class DiscordClient(token: String) : RestClient(token) {
    fun getApiGateway() = getRequest("/gateway").bodyAs(GatewayUrl::class)

    fun getBotGateway() = getRequest("/gateway/bot").bodyAs(GatewayBotUrl::class)

    fun createGuild(guild: CreateGuild) = postRequest("/guilds", guild).bodyAs(Guild::class)

    fun getInvite(inviteCode: String) = getRequest("/invites/$inviteCode").bodyAs(Invite::class)

    fun deleteInvite(inviteCode: String) {
        deleteRequest("/invites/$inviteCode")
    }

    fun getUser(userId: String = "@me") = getRequest("/users/$userId").bodyAs(User::class)

    fun modifyUser(user: ModifyUser) = patchRequest("/users/@me", user).bodyAs(User::class)

    fun getGuilds(limit: Int = 100, before: String? = null, after: String? = null): List<PartialGuild> {
        var url = "/users/@me/guilds?limit=$limit"
        if (before != null) {
            url += "&before=$before"
        }
        if (after != null) {
            url += "&after=$after"
        }
        return getRequest(url).bodyAsListOf(PartialGuild::class)
    }

    fun getDMs() = getRequest("/users/@me/channels").bodyAsListOf(Channel::class)

    fun createDM(createDM: CreateDM) = postRequest("/users/@me/channels", createDM).bodyAs(Channel::class)

    fun createGroupDM(groupDM: CreateGroupDM) = postRequest("/users/@me/channels", groupDM).bodyAs(Channel::class)

    fun getUserConnections() = getRequest("/users/@me/connections").bodyAsListOf(UserConnection::class)

    fun getVoiceRegions() = getRequest("/voice/regions").bodyAsListOf(VoiceRegion::class)
}
