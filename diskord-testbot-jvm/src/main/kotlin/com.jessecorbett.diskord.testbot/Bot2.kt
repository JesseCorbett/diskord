package com.jessecorbett.diskord.testbot


import com.jessecorbett.diskord.AutoGateway
import com.jessecorbett.diskord.api.channel.ChannelClient
import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.gateway.model.GatewayIntents
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.sendMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

suspend fun main() {
    val discordHttpClient = RestClient.default("token")
    val eventDispatcher = EventDispatcher.build(CoroutineScope(SupervisorJob() + Dispatchers.Default))

    eventDispatcher.onReady {
        ChannelClient("channel-id", discordHttpClient).sendMessage("Hello, world!")
    }

    val bot = AutoGateway(
        token = "token",
        intents = GatewayIntents.NON_PRIVILEGED, // Customize to your usage or use the GatewayIntentsComputer like in BotDsl.kt
        restClient = discordHttpClient,
        eventDispatcher = eventDispatcher
    )

    bot.start()
    bot.block() // Blocks until the bot shuts down
}
