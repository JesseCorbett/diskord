package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.interaction.*
import com.jessecorbett.diskord.api.interaction.command.CommandOption
import com.jessecorbett.diskord.bot.BotContext
import kotlin.reflect.KProperty

public class ApplicationCommandBuilder {
    private val delegates = mutableListOf<CommandDelegate<*>>()
    internal val parameters = mutableListOf<CommandOption>()
    internal var callbackFunction: suspend BotContext.(Interaction) -> Unit = {}

    @InteractionModule
    public fun callback(block: suspend BotContext.(Interaction) -> Unit) {
        callbackFunction = block
    }

    internal fun setResponses(responses: List<CommandInteractionOptionResponse>) {
        delegates.forEach { it.responses = responses }
    }

    @InteractionModule
    public fun stringParameter(
        name: String,
        description: String,
        vararg choices: CommandOption.CommandOptionChoice,
        autocomplete: Boolean = false
    ): CommandDelegate<String> {
        parameters += CommandOption.StringOption(
            name = name,
            description = description,
            required = true,
            choices = choices.toList(),
            autocomplete = autocomplete
        )
        return CommandDelegate<String>(name) { responses ->
            responses.filterIsInstance<StringResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun optionalStringParameter(
        name: String,
        description: String,
        vararg choices: CommandOption.CommandOptionChoice,
        autocomplete: Boolean = false
    ): CommandDelegate<String?> {
        parameters += CommandOption.StringOption(
            name = name,
            description = description,
            required = false,
            choices = choices.toList(),
            autocomplete = autocomplete
        )
        return CommandDelegate<String?>(name) { responses ->
            responses.filterIsInstance<StringResponse>().find { it.name == name }?.value
        }.also { delegates += it }
    }

    @InteractionModule
    public fun booleanParameter(
        name: String,
        description: String
    ): CommandDelegate<Boolean> {
        parameters += CommandOption.BooleanOption(
            name = name,
            description = description,
            required = true
        )
        return CommandDelegate<Boolean>(name) { responses ->
            responses.filterIsInstance<BooleanResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun optionalBooleanParameter(
        name: String,
        description: String
    ): CommandDelegate<Boolean?> {
        parameters += CommandOption.BooleanOption(
            name = name,
            description = description,
            required = false
        )
        return CommandDelegate<Boolean?>(name) { responses ->
            responses.filterIsInstance<BooleanResponse>().find { it.name == name }?.value
        }.also { delegates += it }
    }

    @InteractionModule
    public fun intParameter(
        name: String,
        description: String
    ): CommandDelegate<Int> {
        parameters += CommandOption.BooleanOption(
            name = name,
            description = description,
            required = true
        )
        return CommandDelegate<Int>(name) { responses ->
            responses.filterIsInstance<IntegerResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun optionalIntParameter(
        name: String,
        description: String
    ): CommandDelegate<Int?> {
        parameters += CommandOption.IntegerOption(
            name = name,
            description = description,
            required = false
        )
        return CommandDelegate<Int?>(name) { responses ->
            responses.filterIsInstance<IntegerResponse>().find { it.name == name }?.value
        }.also { delegates += it }
    }

    @InteractionModule
    public fun floatParameter(
        name: String,
        description: String
    ): CommandDelegate<Float> {
        parameters += CommandOption.NumberOption(
            name = name,
            description = description,
            required = true
        )
        return CommandDelegate<Float>(name) { responses ->
            responses.filterIsInstance<NumberResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun optionalFloatParameter(
        name: String,
        description: String
    ): CommandDelegate<Float?> {
        parameters += CommandOption.NumberOption(
            name = name,
            description = description,
            required = false
        )
        return CommandDelegate<Float?>(name) { responses ->
            responses.filterIsInstance<NumberResponse>().find { it.name == name }?.value
        }.also { delegates += it }
    }

    @InteractionModule
    public fun userParameter(
        name: String,
        description: String
    ): CommandDelegate<String> {
        parameters += CommandOption.UserOption(
            name = name,
            description = description,
            required = true
        )
        return CommandDelegate<String>(name) { responses ->
            responses.filterIsInstance<UserResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun optionalUserParameter(
        name: String,
        description: String
    ): CommandDelegate<String?> {
        parameters += CommandOption.UserOption(
            name = name,
            description = description,
            required = false
        )
        return CommandDelegate<String?>(name) { responses ->
            responses.filterIsInstance<UserResponse>().find { it.name == name }?.value
        }.also { delegates += it }
    }

    @InteractionModule
    public fun channelParameter(
        name: String,
        description: String
    ): CommandDelegate<String> {
        parameters += CommandOption.ChannelOption(
            name = name,
            description = description,
            required = true
        )
        return CommandDelegate<String>(name) { responses ->
            responses.filterIsInstance<ChannelResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun optionalChannelParameter(
        name: String,
        description: String
    ): CommandDelegate<String?> {
        parameters += CommandOption.ChannelOption(
            name = name,
            description = description,
            required = false
        )
        return CommandDelegate<String?>(name) { responses ->
            responses.filterIsInstance<ChannelResponse>().find { it.name == name }?.value
        }.also { delegates += it }
    }

    @InteractionModule
    public fun roleParameter(
        name: String,
        description: String
    ): CommandDelegate<String> {
        parameters += CommandOption.RoleOption(
            name = name,
            description = description,
            required = true
        )
        return CommandDelegate<String>(name) { responses ->
            responses.filterIsInstance<RoleResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun optionalRoleParameter(
        name: String,
        description: String
    ): CommandDelegate<String?> {
        parameters += CommandOption.RoleOption(
            name = name,
            description = description,
            required = false
        )
        return CommandDelegate<String?>(name) { responses ->
            responses.filterIsInstance<RoleResponse>().find { it.name == name }?.value
        }.also { delegates += it }
    }

    @InteractionModule
    public fun mentionableParameter(
        name: String,
        description: String
    ): CommandDelegate<String> {
        parameters += CommandOption.MentionableOption(
            name = name,
            description = description,
            required = true
        )
        return CommandDelegate<String>(name) { responses ->
            responses.filterIsInstance<MentionableResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun optionalMentionableParameter(
        name: String,
        description: String
    ): CommandDelegate<String?> {
        parameters += CommandOption.MentionableOption(
            name = name,
            description = description,
            required = false
        )
        return CommandDelegate<String?>(name) { responses ->
            responses.filterIsInstance<MentionableResponse>().find { it.name == name }?.value
        }.also { delegates += it }
    }

    public class CommandDelegate<T>(
        public val name: String,
        private val finder: CommandDelegate<T>.(List<CommandInteractionOptionResponse>) -> T
    ) {
        internal var responses: List<CommandInteractionOptionResponse> = emptyList()

        public operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return finder(responses)
        }
    }
}
