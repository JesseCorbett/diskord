package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.interaction.ApplicationCommand
import com.jessecorbett.diskord.api.interaction.command.CommandOption

@InteractionModule
public open class SubcommandGroupBuilder internal constructor() {
    internal val options = mutableListOf<CommandOption.Type>()
    internal val commands: MutableMap<String, CommandContext<ApplicationCommand>> = mutableMapOf()

    @InteractionModule
    public fun slashCommand(
        name: String,
        description: String,
        builder: CommandContext<ApplicationCommand>.() -> Unit
    ) {
        val context = CommandContext<ApplicationCommand>().apply(builder)
        commands[name] = context
        options += CommandOption.SubCommandOption(
            name = name,
            description = description,
            options = context.parameters.map(CommandOption::fromOption)
        )
    }
}

@InteractionModule
public class CommandGroupBuilder internal constructor(): SubcommandGroupBuilder() {
    internal val groups: MutableMap<String, SubcommandGroupBuilder> = mutableMapOf()

    @InteractionModule
    public fun subgroup(
        name: String,
        description: String,
        builder: SubcommandGroupBuilder.() -> Unit
    ) {
        val group = SubcommandGroupBuilder().apply(builder)
        groups[name] = group
        options += CommandOption.SubCommandGroupOption(
            name = name,
            description = description,
            options = group.options.map(CommandOption::fromOption)
        )
    }
}
