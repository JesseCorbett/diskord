package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.common.Permissions
import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.interaction.ApplicationCommand
import com.jessecorbett.diskord.api.interaction.CreateCommand
import com.jessecorbett.diskord.api.interaction.ModalSubmit
import com.jessecorbett.diskord.api.interaction.command.Command
import com.jessecorbett.diskord.api.interaction.command.CommandOption
import com.jessecorbett.diskord.api.interaction.command.CommandType
import com.jessecorbett.diskord.bot.BotContext
import mu.KotlinLogging

@InteractionModule
public class InteractionBuilder(
    private val applicationId: String,
    private val dispatcher: EventDispatcher,
    private val botContext: BotContext,
    private val existingCommands: List<Command>
) {
    private val logger = KotlinLogging.logger {  }
    internal val commandSet: MutableMap<String?, Set<String>> = mutableMapOf(null to emptySet())

    @InteractionModule
    public fun slashCommand(
        name: String,
        description: String,
        guildId: String? = null,
        permissions: Permissions = Permissions.ALL,
        block: suspend ApplicationCommandBuilder<ApplicationCommand.ChatData>.() -> Unit
    ) {
        val createCommand = CreateCommand(
            name = name,
            description = description,
            options = emptyList(),
            defaultPermission = permissions,
            type = CommandType.ChatInput
        )

        interactionCommand(createCommand, guildId, block)
    }

    @InteractionModule
    public fun userCommand(
        name: String,
        guildId: String? = null,
        permissions: Permissions = Permissions.ALL,
        callback: suspend ResponseContext.(ApplicationCommand, ApplicationCommand.UserData) -> Unit
    ) {
        val createCommand = CreateCommand(
            name = name,
            description = "",
            options = emptyList(),
            defaultPermission = permissions,
            type = CommandType.User
        )

        interactionCommand(createCommand, guildId, block = { callback(callback) })
    }

    @InteractionModule
    public fun messageCommand(
        name: String,
        guildId: String? = null,
        permissions: Permissions = Permissions.ALL,
        callback: suspend ResponseContext.(ApplicationCommand, ApplicationCommand.MessageData) -> Unit
    ) {
        val createCommand = CreateCommand(
            name = name,
            description = "",
            options = emptyList(),
            defaultPermission = permissions,
            type = CommandType.Message
        )

        interactionCommand(createCommand, guildId, block = { callback(callback) })
    }

    private fun <D : ApplicationCommand.Data> interactionCommand(
        createCommand: CreateCommand,
        guildId: String? = null,
        block: suspend ApplicationCommandBuilder<D>.() -> Unit
    ) {
        commandSet[guildId] = commandSet.getOrPut(guildId) { mutableSetOf() } + createCommand.name.lowercase()

        var command: Command? = null
        val builder = ApplicationCommandBuilder<D>()

        dispatcher.onInit {
            builder.block()
            // Assemble the command + parameters
            val commandWithParams = createCommand.copy(options = builder.parameters.map { CommandOption.fromOption(it) })

            // Find the existing command, if any
            val existing = existingCommands.filter { it.guildId == guildId }.firstOrNull { it.name == commandWithParams.name }

            // Check if the command already exists and needs updated
            if (existing != null && commandWithParams.options == existing.options) {
                logger.info { "Command with name ${existing.name} and guildId $guildId already exists in current form" }
                command = existing
                return@onInit
            }

            // Create the command
            command = if (guildId != null) {
                botContext.command(applicationId).createGuildCommand(guildId, commandWithParams)
            } else {
                botContext.command(applicationId).createGlobalCommand(commandWithParams)
            }
        }

        val pendingModals = mutableMapOf<String, suspend ResponseContext.(ModalSubmit) -> Unit>()
        val addModalCallback = { mid: String, f: suspend ResponseContext.(ModalSubmit) -> Unit -> pendingModals[mid] = f }
        dispatcher.onInteractionCreate { interaction ->
            if (interaction is ApplicationCommand && interaction.data.commandId == command?.id) {
                val data = interaction.data
                if (data is ApplicationCommand.ChatData) {
                    builder.setResponses(data.options, data.resolvedResources)
                } else {
                    builder.setResponses(emptyList(), null)
                }
                builder.callbackFunction.let { callback ->
                    /*
                    Cast should be safe as we should never have command ID match but somehow have the wrong Data subclass
                     */
                    @Suppress("UNCHECKED_CAST")
                    ResponseContext(botContext, addModalCallback).callback(interaction, data as D)
                }
            } else if (interaction is ModalSubmit) {
                val pending = pendingModals[interaction.data.modalCustomId] ?: return@onInteractionCreate
                pendingModals.remove(interaction.data.modalCustomId)
                ResponseContext(botContext, addModalCallback).pending(interaction)
            }
        }
    }
}
