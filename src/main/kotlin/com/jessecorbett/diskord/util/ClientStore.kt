package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.ChannelClient
import com.jessecorbett.diskord.DiscordClient
import com.jessecorbett.diskord.GuildClient
import com.jessecorbett.diskord.WebhookClient
import com.jessecorbett.diskord.internal.RestClient

class ClientStore(userToken: String) {
    val discord = DiscordClient(userToken)
    val channels = ChannelClients(userToken)
    val guilds = GuildClients(userToken)
    val webhooks = WebhookClients(userToken)
}

class ChannelClients(userToken: String): RestClients<ChannelClient>(userToken, {ChannelClient(userToken, it)})

class GuildClients(userToken: String): RestClients<GuildClient>(userToken, {GuildClient(userToken, it)})

class WebhookClients(userToken: String): RestClients<WebhookClient>(userToken, {WebhookClient(userToken, it)})

abstract class RestClients<T: RestClient>(private val userToken: String, private val gen: (String) -> T) {
    private val clients: MutableMap<String, T> = HashMap()
    operator fun get(resourceId: String) = clients.getOrPut(resourceId) {
        return gen(resourceId)
    }
}
