package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.*
import com.jessecorbett.diskord.api.models.*
import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.api.rest.response.PartialGuild
import com.jessecorbett.diskord.internal.*

class DiscordClient(token: DiscordToken) : RestClient(token) {
    fun getApiGateway() = getRequest("/gateway").bodyAs<GatewayUrl>()

    fun getBotGateway() = getRequest("/gateway/bot").bodyAs<GatewayBotUrl>()

    fun createGuild(guild: CreateGuild) = postRequest("/guilds", guild).bodyAs<Guild>()

    fun getInvite(inviteCode: String) = getRequest("/invites/$inviteCode").bodyAs<Invite>()

    fun deleteInvite(inviteCode: String) {
        deleteRequest("/invites/$inviteCode").close()
    }

    fun getUser(userId: String = "@me") = getRequest("/users/$userId").bodyAs<User>()

    fun modifyUser(user: ModifyUser) = patchRequest("/users/@me", user).bodyAs<User>()

    fun getGuilds(limit: Int = 100, before: String? = null, after: String? = null): List<PartialGuild> {
        var url = "/users/@me/guilds?limit=$limit"
        if (before != null) {
            url += "&before=$before"
        }
        if (after != null) {
            url += "&after=$after"
        }
        return getRequest(url).bodyAsList()
    }

    @Deprecated("Currently Discord does not return anything from the endpoint. Instead messages in DMs fire the CHANNEL_CREATE event and you can get a specific DM channel using createDM()")
    fun getDMs() = getRequest("/users/@me/channels").bodyAsList<Channel>()

    fun createDM(createDM: CreateDM) = postRequest("/users/@me/channels", createDM).bodyAs<Channel>()

    fun createGroupDM(groupDM: CreateGroupDM) = postRequest("/users/@me/channels", groupDM).bodyAs<Channel>()

    fun getUserConnections() = getRequest("/users/@me/connections").bodyAsList<UserConnection>()

    fun getVoiceRegions() = getRequest("/voice/regions").bodyAsList<VoiceRegion>()
}
