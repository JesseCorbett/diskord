package com.jessecorbett.diskord.api.client

import com.jessecorbett.diskord.api.rest.CreateDM
import com.jessecorbett.diskord.api.rest.CreateGroupDM
import com.jessecorbett.diskord.api.rest.CreateGuild
import com.jessecorbett.diskord.api.rest.ModifyUser
import com.jessecorbett.diskord.api.rest.response.PartialGuild
import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.client.internal.RestClient
import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.websocket.GatewayBotUrl
import com.jessecorbett.diskord.api.websocket.GatewayUrl
import com.jessecorbett.diskord.internal.bodyAs
import com.jessecorbett.diskord.internal.bodyAsList

class DiscordClient(token: String, userType: DiscordUserType = DiscordUserType.BOT) : RestClient(token, userType) {
    suspend fun getApiGateway() = getRequest("/websocket").bodyAs<GatewayUrl>()

    suspend fun getBotGateway() = getRequest("/websocket/bot").bodyAs<GatewayBotUrl>()

    suspend fun createGuild(guild: CreateGuild) = postRequest("/guilds", guild).bodyAs<Guild>()

    suspend fun getInvite(inviteCode: String) = getRequest("/invites/$inviteCode").bodyAs<Invite>()

    suspend fun deleteInvite(inviteCode: String) = deleteRequest("/invites/$inviteCode").close()

    suspend fun getUser(userId: String = "@me") = getRequest("/users/$userId").bodyAs<User>()

    suspend fun modifyUser(user: ModifyUser) = patchRequest("/users/@me", user).bodyAs<User>()

    suspend fun getGuilds(limit: Int = 100, before: String? = null, after: String? = null): List<PartialGuild> {
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
    suspend fun getDMs() = getRequest("/users/@me/channels").bodyAsList<Channel>()

    suspend fun createDM(createDM: CreateDM) = postRequest("/users/@me/channels", createDM).bodyAs<Channel>()

    suspend fun createGroupDM(groupDM: CreateGroupDM) = postRequest("/users/@me/channels", groupDM).bodyAs<Channel>()

    suspend fun getUserConnections() = getRequest("/users/@me/connections").bodyAsList<UserConnection>()

    suspend fun getVoiceRegions() = getRequest("/voice/regions").bodyAsList<VoiceRegion>()
}
