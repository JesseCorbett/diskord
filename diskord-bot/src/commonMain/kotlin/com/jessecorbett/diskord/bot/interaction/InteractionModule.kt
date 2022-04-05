package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.interaction.*
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

        val builder = InteractionBuilder(context.botUser.id, dispatcher, context).apply {
            commands()
        }

        /*
         * I want to go on record that this feels like a bad idea in my gut, but I'm not sure why
         * Fingers crossed it is nothing
         * - Jesse
         */
        if (trim && !configuring) {
            val commandClient = context.command(context.botUser.id)
            (context.global().getAllGuilds().map { it.id } + null).forEach {
                val created = builder.commandSet.getOrElse(it) { emptySet() }

                val existing = if (it != null) {
                    commandClient.getGuildCommands(it)
                } else commandClient.getGlobalCommands()

                existing.filter { command -> command.name.lowercase() !in created }.forEach { command ->
                    if (it != null) {
                        commandClient.deleteGuildCommand(it, command.id)
                    } else commandClient.deleteGlobalCommand(command.id)
                }
            }
        }
    }
}
