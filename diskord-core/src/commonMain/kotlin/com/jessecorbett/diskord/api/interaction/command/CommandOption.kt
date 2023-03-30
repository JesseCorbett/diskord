package com.jessecorbett.diskord.api.interaction.command

import com.jessecorbett.diskord.api.common.ChannelType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int

/**
 * Raw data option, to be converted
 *
 * https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-type
 *
 * Highly recommended to convert to and from [CommandOption.Type]
 * using [CommandOption.parsed] and [CommandOption.fromOption]
 */
@Serializable
@JsonClassDiscriminator("type")
public data class CommandOption internal constructor(
    @SerialName("type") public val type: Int,
    @SerialName("name") public val name: String,
    @SerialName("name_localizations") public val localizedNames: Map<String, String> = emptyMap(),
    @SerialName("description") public val description: String,
    @SerialName("description_localizations") public val localizedDescriptions: Map<String, String> = emptyMap(),
    // Below are option specific
    @SerialName("options") public val options: List<CommandOption> = emptyList(),
    @SerialName("required") public val required: Boolean = false,
    @SerialName("choices") public val choices: List<CommandOptionChoice> = emptyList(),
    @SerialName("channel_types") public val channelTypes: List<ChannelType> = emptyList(),
    @SerialName("autocomplete") public val autocomplete: Boolean = false,
    @SerialName("min_value") public val minValue: JsonPrimitive? = null,
    @SerialName("max_value") public val maxValue: JsonPrimitive? = null,
    @SerialName("min_length") public val minLength: Int? = null,
    @SerialName("max_length") public val maxLength: Int? = null
) {

    public fun parsed(): Type = when (type) {
        1 -> SubCommandOption(name, description, localizedNames, localizedDescriptions, options)
        2 -> SubCommandGroupOption(name, description, localizedNames, localizedDescriptions, options)
        3 -> StringOption(name, description, localizedNames, localizedDescriptions, required, choices, autocomplete)
        4 -> IntegerOption(name, description, localizedNames, localizedDescriptions, required, choices, autocomplete, minValue?.int, maxValue?.int)
        5 -> BooleanOption(name, description, localizedNames, localizedDescriptions, required)
        6 -> UserOption(name, description, localizedNames, localizedDescriptions, required)
        7 -> ChannelOption(name, description, localizedNames, localizedDescriptions, required)
        8 -> RoleOption(name, description, localizedNames, localizedDescriptions, required)
        9 -> MentionableOption(name, description, localizedNames, localizedDescriptions, required)
        10 -> NumberOption(name, description, localizedNames, localizedDescriptions, required, choices, autocomplete, minValue?.float, maxValue?.float)
        11 -> AttachmentOption(name, description, localizedNames, localizedDescriptions, required)
        else -> UnknownOption(type, this)
    }

    public companion object {
        public fun fromOption(it: Type): CommandOption = when (it) {
            is SubCommandOption -> CommandOption(it.type, it.name, description = it.description, options = it.options)
            is SubCommandGroupOption -> CommandOption(it.type, it.name, description = it.description)
            is StringOption -> CommandOption(
                it.type,
                it.name,
                description = it.description,
                required = it.required,
                choices = it.choices,
                autocomplete = it.autocomplete
            )
            is IntegerOption -> CommandOption(
                it.type,
                it.name,
                description = it.description,
                required = it.required,
                choices = it.choices,
                autocomplete = it.autocomplete,
                minValue = JsonPrimitive(it.minValue),
                maxValue = JsonPrimitive(it.maxValue)
            )
            is BooleanOption -> CommandOption(it.type, it.name, description = it.description, required = it.required)
            is UserOption -> CommandOption(it.type, it.name, description = it.description, required = it.required)
            is ChannelOption -> CommandOption(it.type, it.name, description = it.description, required = it.required)
            is RoleOption -> CommandOption(it.type, it.name, description = it.description, required = it.required)
            is MentionableOption -> CommandOption(it.type, it.name, description = it.description, required = it.required)
            is NumberOption -> CommandOption(
                it.type,
                it.name,
                description = it.description,
                required = it.required,
                choices = it.choices,
                autocomplete = it.autocomplete,
                minValue = JsonPrimitive(it.minValue),
                maxValue = JsonPrimitive(it.maxValue)
            )
            is AttachmentOption -> CommandOption(it.type, it.name, description = it.description, required = it.required)
            else -> throw IllegalArgumentException("Unknown option type " + it.type)
        }
    }

    public sealed interface Type {
        public val name: String
        public val localizedNames: Map<String, String>
        public val description: String
        public val localizedDescriptions: Map<String, String>
        public val type: Int
    }

    @Serializable
    public data class SubCommandOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("options") val options: List<CommandOption> = emptyList()
    ) : Type {
        override val type: Int = 1
    }

    @Serializable
    public data class SubCommandGroupOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("options") val options: List<CommandOption> = emptyList()
    ) : Type {
        override val type: Int = 2
    }

    @Serializable
    public data class StringOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("required") val required: Boolean = false,
        @SerialName("choices") val choices: List<CommandOptionChoice> = emptyList(),
        @SerialName("autocomplete") val autocomplete: Boolean = false
    ) : Type {
        override val type: Int = 3
    }

    @Serializable
    public data class IntegerOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("required") val required: Boolean = false,
        @SerialName("choices") val choices: List<CommandOptionChoice> = emptyList(),
        @SerialName("autocomplete") val autocomplete: Boolean = false,
        @SerialName("min_value") val minValue: Int? = null,
        @SerialName("max_value") val maxValue: Int? = null
    ) : Type {
        override val type: Int = 4
    }

    @Serializable
    public data class BooleanOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("required") val required: Boolean = false
    ) : Type {
        override val type: Int = 5
    }

    @Serializable
    public data class UserOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("required") val required: Boolean = false
    ) : Type {
        override val type: Int = 6
    }

    @Serializable
    public data class ChannelOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("required") val required: Boolean = false
    ) : Type {
        override val type: Int = 7
    }

    @Serializable
    public data class RoleOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("required") val required: Boolean = false
    ) : Type {
        override val type: Int = 8
    }

    @Serializable
    public data class MentionableOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("required") val required: Boolean = false
    ) : Type {
        override val type: Int = 9
    }

    @Serializable
    public data class NumberOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("required") val required: Boolean = false,
        @SerialName("choices") val choices: List<CommandOptionChoice> = emptyList(),
        @SerialName("autocomplete") val autocomplete: Boolean = false,
        @SerialName("min_value") val minValue: Float? = null,
        @SerialName("max_value") val maxValue: Float? = null
    ) : Type {
        override val type: Int = 10
    }

    @Serializable
    public data class AttachmentOption(
        @SerialName("name") override val name: String,
        @SerialName("description") public override val description: String,
        @SerialName("name_localizations") public override val localizedNames: Map<String, String> = emptyMap(),
        @SerialName("description_localizations") public override val localizedDescriptions: Map<String, String> = emptyMap(),
        @SerialName("required") val required: Boolean = false
    ) : Type {
        override val type: Int = 11
    }

    /**
     * A representation of an option with a code we don't recognize
     */
    @Serializable
    public data class UnknownOption(override val type: Int, val data: CommandOption) : Type {
        override val name: String = "UNKNOWN_OPTION"
        override val localizedNames: Map<String, String> = emptyMap()
        override val description: String = "Diskord did not recognize the code for this Command Option"
        override val localizedDescriptions: Map<String, String> = emptyMap()
    }


    // https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-choice-structure
    @Serializable
    public sealed class CommandOptionChoice {
        @Serializable
        public data class String(
            @SerialName("name") public val name: kotlin.String,
            @SerialName("value") public val value: kotlin.String,
            @SerialName("name_localizations") public val localizedNames: Map<kotlin.String, kotlin.String>
        ) : CommandOptionChoice()

        @Serializable
        public data class Int(
            @SerialName("name") public val name: kotlin.String,
            @SerialName("value") public val value: kotlin.Int,
            @SerialName("name_localizations") public val localizedNames: Map<kotlin.String, kotlin.String>
        ) : CommandOptionChoice()

        @Serializable
        public data class Double(
            @SerialName("name") public val name: kotlin.String,
            @SerialName("value") public val value: kotlin.Double,
            @SerialName("name_localizations") public val localizedNames: Map<kotlin.String, kotlin.String>
        ) : CommandOptionChoice()
    }
}
