package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.common.Attachment
import com.jessecorbett.diskord.api.interaction.*
import com.jessecorbett.diskord.api.interaction.command.CommandOption
import kotlin.reflect.KProperty

@InteractionModule
public class CommandContext<I: Interaction> {
    private val delegates = mutableListOf<CommandDelegate<*>>()
    internal val parameters = mutableListOf<CommandOption.Type>()
    private var callbackFunction: suspend ResponseContext<I>.() -> Unit = {}

    @InteractionModule
    public fun callback(block: suspend ResponseContext<I>.() -> Unit) {
        callbackFunction = block
    }

    internal suspend fun respond(response: ResponseContext<I>) {
        delegates.forEach { it.responseContext = response }
        response.callbackFunction()
    }

    @InteractionModule
    public fun stringParameter(
        name: String,
        description: String,
        vararg choices: CommandOption.CommandOptionChoice,
        autocomplete: Boolean = false,
        optional: Boolean = false
    ): CommandDelegate<String?> {
        parameters += CommandOption.StringOption(
            name = name,
            description = description,
            required = !optional,
            choices = choices.toList(),
            autocomplete = autocomplete
        )
        return CommandDelegate(name) { responses, _ ->
            responses.filterIsInstance<StringResponse>().find { it.name == name }.let {
                if (it == null && optional) null else it!!.value // TODO error handling
            }
        }.also { delegates += it }
    }

    @InteractionModule
    public fun booleanParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<Boolean?> {
        parameters += CommandOption.BooleanOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses, _ ->
            responses.filterIsInstance<BooleanResponse>().find { it.name == name }.let {
                if (it == null && optional) null else it!!.value // TODO error handling
            }
        }.also { delegates += it }
    }

    @InteractionModule
    public fun intParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<Int?> {
        parameters += CommandOption.BooleanOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses, _ ->
            responses.filterIsInstance<IntegerResponse>().find { it.name == name }.let {
                if (it == null && optional) null else it!!.value // TODO error handling
            }
        }.also { delegates += it }
    }

    @InteractionModule
    public fun floatParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<Float?> {
        parameters += CommandOption.NumberOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses, _ ->
            responses.filterIsInstance<NumberResponse>().find { it.name == name }.let {
                if (it == null && optional) null else it!!.value // TODO error handling
            }
        }.also { delegates += it }
    }

    @InteractionModule
    public fun userParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<String?> {
        parameters += CommandOption.UserOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses, _ ->
            responses.filterIsInstance<UserResponse>().find { it.name == name }.let {
                if (it == null && optional) null else it!!.value // TODO error handling
            }
        }.also { delegates += it }
    }

    @InteractionModule
    public fun channelParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<String?> {
        parameters += CommandOption.ChannelOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses, _ ->
            responses.filterIsInstance<ChannelResponse>().find { it.name == name }.let {
                if (it == null && optional) null else it!!.value // TODO error handling
            }
        }.also { delegates += it }
    }

    @InteractionModule
    public fun roleParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<String?> {
        parameters += CommandOption.RoleOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses, _ ->
            responses.filterIsInstance<RoleResponse>().find { it.name == name }.let {
                if (it == null && optional) null else it!!.value // TODO error handling
            }
        }.also { delegates += it }
    }

    @InteractionModule
    public fun mentionableParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<String?> {
        parameters += CommandOption.MentionableOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses, _ ->
            responses.filterIsInstance<MentionableResponse>().find { it.name == name }.let {
                if (it == null && optional) null else it!!.value // TODO error handling
            }
        }.also { delegates += it }
    }

    @InteractionModule
    public fun attachmentParameter(
        name: String,
        description: String,
        optional: Boolean = false
    ): CommandDelegate<Attachment?> {
        parameters += CommandOption.AttachmentOption(
            name = name,
            description = description,
            required = !optional
        )
        return CommandDelegate(name) { responses, data ->
            val snowflake = responses.filterIsInstance<AttachmentResponse>().find { it.name == name }.let {
                if (it == null && optional) null else it!!.value // TODO error handling
            } ?: return@CommandDelegate null
            data!!.attachments.getValue(snowflake)
        }.also { delegates += it }
    }

    public class CommandDelegate<T>(
        public val name: String,
        public var responseContext: ResponseContext<*>? = null,
        private val finder: (List<CommandInteractionOptionResponse>, CommandInteractionDataResolved?) -> T
    ) {
        public operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            val context = requireNotNull(responseContext) { "CommandDelegate requires being primed with a ResponseContext before it can get values" }
            return finder(context.options, context.data)
        }
    }
}
