package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.interaction.ApplicationCommand
import com.jessecorbett.diskord.api.interaction.CreateCommand
import com.jessecorbett.diskord.api.interaction.command.Command
import com.jessecorbett.diskord.api.interaction.command.CommandType
import com.jessecorbett.diskord.bot.BotContext

@InteractionModule
public class InteractionBuilder(
    private val applicationId: String,
    private val dispatcher: EventDispatcher<Unit>,
    private val botContext: BotContext
) {
    internal val commandSet: MutableMap<String?, Set<String>> = mutableMapOf(null to emptySet())

    @InteractionModule
    public fun slashCommand(
        name: String,
        description: String,
        guildId: String? = null,
        availableByDefault: Boolean = true,
        block: suspend ApplicationCommandBuilder<ApplicationCommand.ChatData>.() -> Unit
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
        callback: suspend BotContext.(ApplicationCommand, ApplicationCommand.UserData) -> Unit
    ) {
        val createCommand = CreateCommand(
            name = name,
            description = "",
            options = emptyList(),
            defaultPermission = availableByDefault,
            type = CommandType.User
        )

        applicationCommand<ApplicationCommand.UserData>(createCommand, guildId, block = { callback(callback) })
    }

    @InteractionModule
    public fun messageCommand(
        name: String,
        guildId: String? = null,
        availableByDefault: Boolean = true,
        callback: suspend BotContext.(ApplicationCommand, ApplicationCommand.MessageData) -> Unit
    ) {
        val createCommand = CreateCommand(
            name = name,
            description = "",
            options = emptyList(),
            defaultPermission = availableByDefault,
            type = CommandType.Message
        )

        applicationCommand<ApplicationCommand.MessageData>(createCommand, guildId, block = { callback(callback) })
    }

    private fun <D: ApplicationCommand.Data> applicationCommand(
        createCommand: CreateCommand,
        guildId: String? = null,
        block: suspend ApplicationCommandBuilder<D>.() -> Unit
    ) {
        commandSet[guildId] = commandSet.getOrPut(guildId) { mutableSetOf() } + createCommand.name.lowercase()

        var command: Command? = null
        val builder = ApplicationCommandBuilder<D>()

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
                val data = interaction.data
                if (data is ApplicationCommand.ChatData) {
                    builder.setResponses(data.options)
                } else {
                    builder.setResponses(emptyList())
                }
                builder.callbackFunction.let { callback ->
                    /*
                    Cast should be safe as we should never have command ID match but somehow have the wrong Data subclass
                     */
                    @Suppress("UNCHECKED_CAST")
                    botContext.callback(interaction, data as D)
                }
            }
        }
    }
}
