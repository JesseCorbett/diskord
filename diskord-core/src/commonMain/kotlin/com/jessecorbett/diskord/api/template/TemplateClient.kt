package com.jessecorbett.diskord.api.template

import com.jessecorbett.diskord.api.common.Guild
import com.jessecorbett.diskord.api.common.Template
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.call.*

/**
 * A REST client for a specific template
 *
 * @param templateCode The id of the template
 * @param client The REST client implementation
 */
@OptIn(DiskordInternals::class)
public class TemplateClient(public val templateCode: String, client: RestClient) : RestClient by client {

    /**
     * Get the server template
     *
     * @return The template
     */
    public suspend fun getTemplate(): Template {
        return GET("/guilds/templates", "/$templateCode").body()
    }

    /**
     * Create a guild from the template
     *
     * @return The created guild
     */
    public suspend fun createGuild(): Guild {
        return POST("/guilds/templates", "/$templateCode").body()
    }
}
