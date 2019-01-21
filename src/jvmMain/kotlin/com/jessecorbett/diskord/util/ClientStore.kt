package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.rest.client.ChannelClient
import com.jessecorbett.diskord.api.rest.client.DiscordClient
import com.jessecorbett.diskord.api.rest.client.GuildClient
import com.jessecorbett.diskord.api.rest.client.WebhookClient
import com.jessecorbett.diskord.api.rest.client.internal.RestClient
import java.util.concurrent.ConcurrentHashMap

/**
 * A container of [RestClients] for each of the 3 resource clients and the [DiscordClient].
 *
 * @param userToken the user token used by each client.
 */
class ClientStore(userToken: String) {
    /**
     * Managed discord client instance.
     */
    val discord = DiscordClient(userToken)

    /**
     * Managed channel clients instance.
     */
    val channels = ChannelClients(userToken)

    /**
     * Managed guild clients instance.
     */
    val guilds = GuildClients(userToken)

    /**
     * Managed webhook clients instance.
     */
    val webhooks = WebhookClients(userToken)
}

/**
 * A group of [ChannelClient] instances for a given user token.
 *
 * This class will automatically create a [ChannelClient] if one does not already exist for a given channelId.
 *
 * @param userToken the user token used by each [ChannelClient].
 * @constructor Creates an empty group of clients.
 */
class ChannelClients(userToken: String): RestClients<ChannelClient>(userToken, { ChannelClient(userToken, it) })


/**
 * A group of [GuildClient] instances for a given user token.
 *
 * This class will automatically create a [GuildClient] if one does not already exist for a given guildId.
 *
 * @param userToken the user token used by each [GuildClient].
 * @constructor Creates an empty group of clients.
 */
class GuildClients(userToken: String): RestClients<GuildClient>(userToken, { GuildClient(userToken, it) })


/**
 * A group of [WebhookClient] instances for a given user token.
 *
 * This class will automatically create a [WebhookClient] if one does not already exist for a given webhookId.
 *
 * @param userToken the user token used by each [WebhookClient].
 * @constructor Creates an empty group of clients.
 */
class WebhookClients(userToken: String): RestClients<WebhookClient>(userToken, { WebhookClient(userToken, it) })

/**
 * Generic container for [RestClient] instances.
 *
 * Automatically creates and stores [RestClient] instances based on the id relevant to each client instance.
 *
 * @param T the RestClient implementation class.
 * @param userToken the user token used for authentication by each [RestClient].
 * @param gen a lambda which returns a new instance of class T when requested by the user.
 * @constructor Creates an instance and sets up a [ConcurrentHashMap] backing the group.
 */
abstract class RestClients<T: RestClient>(private val userToken: String, private val gen: (String) -> T) {
    private val clients: MutableMap<String, T> = ConcurrentHashMap()

    /**
     * Gets a [RestClient] implemented by class T for the given resourceId, creating it if it doesn't exist.
     *
     * @param resourceId the id of the requested resource client, such as channelId or guildId
     *
     * @return a [RestClient] implementation for the specified resource
     */
    operator fun get(resourceId: String) = clients.getOrPut(resourceId) { gen(resourceId) }
}
