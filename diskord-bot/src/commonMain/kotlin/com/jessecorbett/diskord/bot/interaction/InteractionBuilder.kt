package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.interaction.ApplicationCommand
import com.jessecorbett.diskord.api.interaction.CreateCommand
import com.jessecorbett.diskord.api.interaction.command.Command
import com.jessecorbett.diskord.bot.BotContext

@InteractionModule
public class InteractionBuilder(
    private val applicationId: String,
    private val dispatcher: EventDispatcher<Unit>,
    private val botContext: BotContext
) {
    @InteractionModule
    public fun slashCommand(name: String, description: String, guildId: String? = null, block: suspend SlashCommandBuilder.() -> Unit) {
        var command: Command? = null
        val builder = SlashCommandBuilder()

        dispatcher.onReady {
            builder.block()
            command = if (guildId != null) {
                botContext.command(applicationId).createGuildCommand(
                    guildId, CreateCommand(
                        name = name,
                        description = description,
                        options = builder.parameters
                    )
                )
            } else {
                botContext.command(applicationId).createGlobalCommand(
                    CreateCommand(
                        name = name,
                        description = description,
                        options = builder.parameters
                    )
                )
            }
        }

        dispatcher.onInteractionCreate { interaction ->
            if (interaction is ApplicationCommand && interaction.data.commandId == command?.id) {
                builder.setResponses(interaction.data.options)
                builder.callbackFunction.let { callback ->
                    botContext.callback(interaction)
                }
            }
        }
    }
}
