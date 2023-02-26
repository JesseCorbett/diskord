package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.common.Attachment
import com.jessecorbett.diskord.api.interaction.ApplicationCommand
import com.jessecorbett.diskord.api.interaction.AttachmentResponse
import com.jessecorbett.diskord.api.interaction.BooleanResponse
import com.jessecorbett.diskord.api.interaction.ChannelResponse
import com.jessecorbett.diskord.api.interaction.CommandInteractionDataResolved
import com.jessecorbett.diskord.api.interaction.CommandInteractionOptionResponse
import com.jessecorbett.diskord.api.interaction.IntegerResponse
import com.jessecorbett.diskord.api.interaction.MentionableResponse
import com.jessecorbett.diskord.api.interaction.NumberResponse
import com.jessecorbett.diskord.api.interaction.RoleResponse
import com.jessecorbett.diskord.api.interaction.StringResponse
import com.jessecorbett.diskord.api.interaction.UserResponse
import com.jessecorbett.diskord.api.interaction.command.CommandOption
import kotlin.reflect.KProperty

public class ApplicationCommandBuilder<D : ApplicationCommand.Data> {
    private val delegates = mutableListOf<CommandDelegate<*>>()
    internal val parameters = mutableListOf<CommandOption.Type>()
    internal var callbackFunction: suspend ResponseContext.(ApplicationCommand, D) -> Unit = { _, _ -> }

    @InteractionModule
    public fun callback(block: suspend ResponseContext.(ApplicationCommand, D) -> Unit) {
        callbackFunction = block
    }

    internal fun setResponses(responses: List<CommandInteractionOptionResponse>, resources: CommandInteractionDataResolved?) {
        delegates.forEach {
            it.responses = responses
            it.resources = resources
        }
    }

    @InteractionModule
    public fun stringParameter(
        name: String,
        description: String,
        vararg choices: CommandOption.CommandOptionChoice,
        autocomplete: Boolean = false,
        optional: Boolean = false
    ): CommandDelegate<String> {
        parameters += CommandOption.StringOption(
            name = name,
            description = description,
            required = !optional,
            choices = choices.toList(),
            autocomplete = autocomplete
        )
        return CommandDelegate(name) { responses ->
            responses.filterIsInstance<StringResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun booleanParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<Boolean> {
        parameters += CommandOption.BooleanOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses ->
            responses.filterIsInstance<BooleanResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun intParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<Int> {
        parameters += CommandOption.BooleanOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses ->
            responses.filterIsInstance<IntegerResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun floatParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<Float> {
        parameters += CommandOption.NumberOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses ->
            responses.filterIsInstance<NumberResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun userParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<String> {
        parameters += CommandOption.UserOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses ->
            responses.filterIsInstance<UserResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun channelParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<String> {
        parameters += CommandOption.ChannelOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses ->
            responses.filterIsInstance<ChannelResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun roleParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<String> {
        parameters += CommandOption.RoleOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses ->
            responses.filterIsInstance<RoleResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun mentionableParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<String> {
        parameters += CommandOption.MentionableOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses ->
            responses.filterIsInstance<MentionableResponse>().find { it.name == name }!!.value!! // TODO error handling
        }.also { delegates += it }
    }

    @InteractionModule
    public fun attachmentParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<Attachment> {
        parameters += CommandOption.AttachmentOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses ->
            val snowflake = responses.filterIsInstance<AttachmentResponse>().find { it.name == name }!!.value!! // TODO error handling
            resources!!.attachments.getValue(snowflake)
        }.also { delegates += it }
    }

    public class CommandDelegate<T>(
        public val name: String,
        private val finder: CommandDelegate<T>.(List<CommandInteractionOptionResponse>) -> T
    ) {
        internal var responses: List<CommandInteractionOptionResponse> = emptyList()
        internal var resources: CommandInteractionDataResolved? = null

        public operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return finder(responses)
        }
    }
}
