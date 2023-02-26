package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.interaction.InteractionPing
import com.jessecorbett.diskord.api.interaction.callback.PingResponse
import com.jessecorbett.diskord.bot.BotBase
import com.jessecorbett.diskord.util.getAllGuilds

@DslMarker
public annotation class InteractionModule

/**
 * Installs the interactions DSL and provides a scope for defining commands
 *
 * @param trim Whether to trim commands that were not upserted by this instance
 */
@InteractionModule
public fun BotBase.interactions(trim: Boolean = true, commands: InteractionBuilder.() -> Unit) {
    registerModule { dispatcher, context, configuring ->

        // Handle pings automatically
        dispatcher.onInteractionCreate { interaction ->
            if (interaction is InteractionPing) {
                context.interaction(context.botUser.id, interaction.token).createInteractionResponse(
                    interactionId = interaction.id,
                    interactionResponse = PingResponse
                )
            }
        }

        val commandClient = context.command(context.botUser.id)

        val existingCommands = (context.global().getAllGuilds().map { it.id } + null).flatMap { guildId ->
            if (guildId != null) {
                commandClient.getGuildCommands(guildId)
            } else commandClient.getGlobalCommands()
        }

        val builder = InteractionBuilder(context.botUser.id, dispatcher, context, existingCommands).apply {
            commands()
        }

        // Check each existing command to see if the builder expects it to be around, and delete it if not
        if (trim && !configuring) {
            existingCommands.forEach { existing ->
                val guildId = existing.guildId
                if (existing.name !in (builder.commandSet[guildId] ?: emptySet())) {
                    if (guildId != null) {
                        commandClient.deleteGuildCommand(guildId, existing.id)
                    } else commandClient.deleteGlobalCommand(existing.id)
                }
            }
        }
    }
}
