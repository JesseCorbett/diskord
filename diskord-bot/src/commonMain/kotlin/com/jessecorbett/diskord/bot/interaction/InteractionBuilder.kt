package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.interaction.ApplicationCommand
import com.jessecorbett.diskord.api.interaction.CreateCommand
import com.jessecorbett.diskord.api.interaction.Interaction
import com.jessecorbett.diskord.api.interaction.command.Command
import com.jessecorbett.diskord.api.interaction.command.CommandType
import com.jessecorbett.diskord.bot.BotContext

@InteractionModule
public class InteractionBuilder(
    private val applicationId: String,
    private val dispatcher: EventDispatcher<Unit>,
    private val botContext: BotContext
) {
    @InteractionModule
    public fun slashCommand(
        name: String,
        description: String,
        guildId: String? = null,
        availableByDefault: Boolean = true,
        block: suspend ApplicationCommandBuilder.() -> Unit
    ) {
        val createCommand = CreateCommand(
            name = name,
            description = description,
            options = emptyList(),
            defaultPermission = availableByDefault,
            type = CommandType.ChatInput
        )

        applicationCommand(createCommand, guildId, block)
    }

    @InteractionModule
    public fun userCommand(
        name: String,
        guildId: String? = null,
        availableByDefault: Boolean = true,
        callback: suspend BotContext.(Interaction) -> Unit
    ) {
        val createCommand = CreateCommand(
            name = name,
            description = "",
            options = emptyList(),
            defaultPermission = availableByDefault,
            type = CommandType.User
        )

        applicationCommand(createCommand, guildId, block = { callback(callback) })
    }

    @InteractionModule
    public fun messageCommand(
        name: String,
        guildId: String? = null,
        availableByDefault: Boolean = true,
        callback: suspend BotContext.(Interaction) -> Unit
    ) {
        val createCommand = CreateCommand(
            name = name,
            description = "",
            options = emptyList(),
            defaultPermission = availableByDefault,
            type = CommandType.Message
        )

        applicationCommand(createCommand, guildId, block = { callback(callback) })
    }

    private fun applicationCommand(
        createCommand: CreateCommand,
        guildId: String? = null,
        block: suspend ApplicationCommandBuilder.() -> Unit
    ) {
        var command: Command? = null
        val builder = ApplicationCommandBuilder()

        dispatcher.onReady {
            builder.block()
            command = if (guildId != null) {
                botContext.command(applicationId).createGuildCommand(guildId, createCommand)
            } else {
                botContext.command(applicationId).createGlobalCommand(createCommand)
            }
        }

        dispatcher.onInteractionCreate { interaction ->
            if (interaction is ApplicationCommand && interaction.data.commandId == command?.id) {
                println(interaction)
                val data = interaction.data
                if (data is ApplicationCommand.ChatData) {
                    builder.setResponses(data.options)
                } else {
                    builder.setResponses(emptyList())
                }
                builder.callbackFunction.let { callback ->
                    botContext.callback(interaction)
                }
            }
        }
    }
}
