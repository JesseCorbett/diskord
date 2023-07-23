package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.common.Permissions
import com.jessecorbett.diskord.api.exceptions.DiscordException
import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.interaction.*
import com.jessecorbett.diskord.api.interaction.command.Command
import com.jessecorbett.diskord.api.interaction.command.CommandOption
import com.jessecorbett.diskord.api.interaction.command.CommandType
import com.jessecorbett.diskord.bot.BotContext
import io.github.oshai.kotlinlogging.KotlinLogging

@InteractionModule
public class InteractionBuilder(
    private val applicationId: String,
    private val dispatcher: EventDispatcher,
    private val botContext: BotContext,
    private val existingCommands: List<Command>
) {
    private val logger = KotlinLogging.logger {  }
    internal val commandSet: MutableMap<String?, Set<String>> = mutableMapOf(null to emptySet())

    /**
     * A chat based command, prefixed with a `/`
     */
    @InteractionModule
    public fun slashCommand(
        name: String,
        description: String,
        guildId: String? = null,
        permissions: Permissions = Permissions.ALL,
        build: CommandContext<ApplicationCommand>.() -> Unit
    ) {
        val context = CommandContext<ApplicationCommand>().apply(build)

        val createCommand = CreateCommand(
            name = name,
            description = description,
            options = context.parameters.map(CommandOption::fromOption),
            defaultPermission = permissions,
            type = CommandType.ChatInput
        )

        interactionCommand(createCommand, guildId) { responseContext ->
            context.respond(responseContext)
        }
    }

    /**
     * An extension of [slashCommand] which allows multiple slash commands to be loosely grouped
     */
    @InteractionModule
    public fun commandGroup(
        name: String,
        description: String,
        guildId: String? = null,
        permissions: Permissions = Permissions.ALL,
        builder: CommandGroupBuilder.() -> Unit
    ) {
        val groupTree = CommandGroupBuilder().apply(builder)
        val createCommand = CreateCommand(
            name = name,
            description = description,
            options = groupTree.options.map(CommandOption::fromOption),
            defaultPermission = permissions,
            type = CommandType.ChatInput
        )

        interactionCommand(createCommand, guildId) { responseContext ->
            responseContext.options.forEach { opt ->
                when (opt) {
                    is SubCommandResponse -> {
                        groupTree.commands[opt.name]?.let { command ->
                            val tunedContext = responseContext.copy(options = opt.options)
                            command.respond(tunedContext)
                        } ?: logger.error { "Could not find subcommand '${opt.name}' in command group name '$name' but received a response for it" }
                    }
                    is SubCommandGroupResponse -> {
                        groupTree.groups[opt.name]?.let { group ->
                            opt.options.filterIsInstance<SubCommandResponse>().forEach { subcommand ->
                                group.commands[subcommand.name]?.let { command ->
                                    val tunedContext = responseContext.copy(options = subcommand.options)
                                    command.respond(tunedContext)
                                } ?: logger.error { "Could not find subcommand '${opt.name} -> ${subcommand.name}' in command group name '$name' but received a response for it" }
                            }
                        } ?: logger.error { "Could not find subgroup '${opt.name}' in command group name '$name' but received a response for it" }
                    }
                    else -> logger.error { "Entered an error case where command group '$name' received an ${opt::class.simpleName}" }
                }
            }
        }
    }

    /**
     * A command available in the context menu of a user
     */
    @InteractionModule
    public fun userCommand(
        name: String,
        guildId: String? = null,
        permissions: Permissions = Permissions.ALL,
        callback: suspend ResponseContext<ApplicationCommand>.() -> Unit
    ) {
        val createCommand = CreateCommand(
            name = name,
            description = "",
            defaultPermission = permissions,
            type = CommandType.User
        )

        interactionCommand(createCommand, guildId) { responseContext ->
            responseContext.callback()
        }
    }

    /**
     * A command available in the context menu of a message
     */
    @InteractionModule
    public fun messageCommand(
        name: String,
        guildId: String? = null,
        permissions: Permissions = Permissions.ALL,
        callback: suspend ResponseContext<ApplicationCommand>.() -> Unit
    ) {
        val createCommand = CreateCommand(
            name = name,
            description = "",
            defaultPermission = permissions,
            type = CommandType.Message
        )

        interactionCommand(createCommand, guildId) { responseContext ->
            responseContext.callback()
        }
    }

    private fun interactionCommand(
        createCommand: CreateCommand,
        guildId: String? = null,
        callback: suspend (ResponseContext<ApplicationCommand>) -> Unit
    ) {
        commandSet[guildId] = commandSet.getOrPut(guildId) { mutableSetOf() } + createCommand.name.lowercase()

        var command: Command? = null

        // Register the command
        dispatcher.onInit {
            // Find the existing command, if any
            val existing = existingCommands.filter { it.guildId == guildId }.firstOrNull { it.name == createCommand.name }

            // Check if the command already exists and needs updated
            if (existing != null && createCommand.options?.toSet() == existing.options.toSet()) {
                logger.debug { "Command with name ${existing.name} and guildId $guildId already exists in current form" }
                command = existing
                return@onInit
            }

            // Create the command
            command = try {
                if (guildId != null) {
                    botContext.command(applicationId).createGuildCommand(guildId, createCommand)
                } else {
                    botContext.command(applicationId).createGlobalCommand(createCommand)
                }
            } catch (e: DiscordException) {
                logger.error { "Some error occurred preventing the command '${createCommand.name}' from being registered" }
                throw e
            }
        }

        // We have to store modals pending here because they're stateful, but Discord doesn't handle the state for us
        val pendingModals = mutableMapOf<String, suspend ResponseContext<ModalSubmit>.(ModalSubmit) -> Unit>()

        // Registers a pending modal to be handled later
        val addModalCallback: (String, suspend ResponseContext<ModalSubmit>.(ModalSubmit) -> Unit) -> Unit = { mid, f ->
            pendingModals[mid] = f
        }

        // Handle the command being used
        dispatcher.onInteractionCreate { interaction ->
            // Handle an application command submission
            if (interaction is ApplicationCommand && interaction.data.commandId == command?.id) {
                val responseContext = when (val data = interaction.data) {
                    is ApplicationCommand.ChatData -> ResponseContext(botContext, interaction, data.options, data.resolvedResources, addModalCallback)
                    is ApplicationCommand.MessageData -> ResponseContext(botContext, interaction, emptyList(), data.convertedUsersRolesChannels, addModalCallback)
                    is ApplicationCommand.UserData -> ResponseContext(botContext, interaction, emptyList(), data.convertedUsersRolesChannels, addModalCallback)
                }

                callback(responseContext)
            } else if (interaction is ModalSubmit) { // Handle a modal submission

                val pending = pendingModals[interaction.data.modalCustomId] ?: return@onInteractionCreate
                pendingModals.remove(interaction.data.modalCustomId)
                ResponseContext(botContext, interaction, emptyList(), null, addModalCallback).pending(interaction)
            }
        }
    }
}
