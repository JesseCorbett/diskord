package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.rest.client.ChannelClient
import com.jessecorbett.diskord.api.rest.client.DiscordClient
import com.jessecorbett.diskord.api.rest.client.GuildClient
import com.jessecorbett.diskord.api.rest.client.WebhookClient
import com.jessecorbett.diskord.api.rest.client.internal.RestClient

class ClientStore(userToken: String) {
    val discord = DiscordClient(userToken)
    val channels = ChannelClients(userToken)
    val guilds = GuildClients(userToken)
    val webhooks = WebhookClients(userToken)
}

class ChannelClients(userToken: String): RestClients<ChannelClient>(userToken, { ChannelClient(userToken, it) })

class GuildClients(userToken: String): RestClients<GuildClient>(userToken, { GuildClient(userToken, it) })

class WebhookClients(userToken: String): RestClients<WebhookClient>(userToken, { WebhookClient(userToken, it) })

abstract class RestClients<T: RestClient>(private val userToken: String, private val gen: (String) -> T) {
    private val clients: MutableMap<String, T> = HashMap()
    operator fun get(resourceId: String) = clients.getOrPut(resourceId) {
        return gen(resourceId)
    }
}
