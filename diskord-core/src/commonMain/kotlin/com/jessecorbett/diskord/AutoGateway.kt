package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.gateway.EventHandler
import com.jessecorbett.diskord.api.gateway.GatewaySession
import com.jessecorbett.diskord.api.gateway.model.GatewayIntents
import com.jessecorbett.diskord.api.global.GatewayBotUrl
import com.jessecorbett.diskord.api.global.GlobalClient
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import mu.KotlinLogging

/**
 * A utility class for automatically creating and managing sharded gateway sessions
 */
public class AutoGateway @OptIn(DiskordInternals::class) constructor(
    private val token: String,
    private val intents: GatewayIntents = GatewayIntents.NON_PRIVILEGED,
    private val eventScope: CoroutineScope,
    restClient: RestClient,
    private val eventHandler: EventHandler
) {

    private val logger = KotlinLogging.logger {}
    private val globalClient = GlobalClient(restClient)
    private var sessions: List<GatewaySession> = emptyList()

    /**
     * Starts the gateway sessions if they aren't already running
     */
    public suspend fun start(): AutoGateway {
        if (sessions.isNotEmpty()) {
            logger.warn { "Attempted to start an AutoGateway that had already started" }
            return this
        }
        val botUrl = globalClient.getBotGateway()
        if (botUrl.shards > 1) {
            for (i in 0 until botUrl.shards) {
                sessions = sessions + createSession(botUrl, botUrl.shards, i)
            }
        } else {
            sessions = listOf(createSession(botUrl, 0, 0))
        }

        return this
    }

    /**
     * Blocks the current process in case your program is only the bot
     */
    public suspend fun block() {
        while (sessions.any { it.running }) {
            delay(100) // If we could find a way to make Gateway a session or similar this would be better I think
        }
    }

    /**
     * Closes all the gateway sessions
     */
    public suspend fun stop() {
        if (sessions.isEmpty()) {
            logger.warn { "Attempted to stop an AutoGateway that was not running" }
            return
        }
        sessions.forEach { it.closeSession() }
        sessions = emptyList()
    }

    private suspend fun createSession(url: GatewayBotUrl, shards: Int, shard: Int): GatewaySession {
        return GatewaySession(token, url, intents, eventScope, shards, shard, eventHandler).also {
            it.startSession()
        }
    }
}
