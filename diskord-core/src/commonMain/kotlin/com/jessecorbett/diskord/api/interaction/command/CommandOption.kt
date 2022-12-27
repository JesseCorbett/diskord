package com.jessecorbett.diskord.api.interaction.command

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
public sealed class CommandOption {
    public abstract val name: String
    public abstract val description: String

    @Serializable
    @SerialName("1")
    public data class SubCommandOption(
        @SerialName("name") override val name: String,
        @SerialName("description") override val description: String,
        @SerialName("options") val options: List<CommandOption> = emptyList()
    ) : CommandOption()

    @Serializable
    @SerialName("2")
    public data class SubCommandGroupOption(
        @SerialName("name") override val name: String,
        @SerialName("description") override val description: String,
        @SerialName("options") val options: List<CommandOption> = emptyList()
    ) : CommandOption()

    @Serializable
    @SerialName("3")
    public data class StringOption(
        @SerialName("name") override val name: String,
        @SerialName("description") override val description: String,
        @SerialName("required") val required: Boolean = false,
        @SerialName("choices") val choices: List<CommandOptionChoice> = emptyList(),
        @SerialName("autocomplete") val autocomplete: Boolean = false
    ) : CommandOption()

    @Serializable
    @SerialName("4")
    public data class IntegerOption(
        @SerialName("name") override val name: String,
        @SerialName("description") override val description: String,
        @SerialName("required") val required: Boolean = false,
        @SerialName("choices") val choices: List<CommandOptionChoice> = emptyList(),
        @SerialName("autocomplete") val autocomplete: Boolean = false,
        @SerialName("min_value") val minValue: Int? = null,
        @SerialName("max_value") val maxValue: Int? = null
    ) : CommandOption()

    @Serializable
    @SerialName("5")
    public data class BooleanOption(
        @SerialName("name") override val name: String,
        @SerialName("description") override val description: String,
        @SerialName("required") val required: Boolean = false
    ) : CommandOption()

    @Serializable
    @SerialName("6")
    public data class UserOption(
        @SerialName("name") override val name: String,
        @SerialName("description") override val description: String,
        @SerialName("required") val required: Boolean = false
    ) : CommandOption()

    @Serializable
    @SerialName("7")
    public data class ChannelOption(
        @SerialName("name") override val name: String,
        @SerialName("description") override val description: String,
        @SerialName("required") val required: Boolean = false
    ) : CommandOption()

    @Serializable
    @SerialName("8")
    public data class RoleOption(
        @SerialName("name") override val name: String,
        @SerialName("description") override val description: String,
        @SerialName("required") val required: Boolean = false
    ) : CommandOption()

    @Serializable
    @SerialName("9")
    public data class MentionableOption(
        @SerialName("name") override val name: String,
        @SerialName("description") override val description: String,
        @SerialName("required") val required: Boolean = false
    ) : CommandOption()

    @Serializable
    @SerialName("10")
    public data class NumberOption(
        @SerialName("name") override val name: String,
        @SerialName("description") override val description: String,
        @SerialName("required") val required: Boolean = false,
        @SerialName("choices") val choices: List<CommandOptionChoice> = emptyList(),
        @SerialName("autocomplete") val autocomplete: Boolean = false,
        @SerialName("min_value") val minValue: Float? = null,
        @SerialName("max_value") val maxValue: Float? = null
    ) : CommandOption()


    // https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-choice-structure
    @Serializable
    public sealed class CommandOptionChoice {
        @Serializable
        public data class String(
            @SerialName("name") public val name: kotlin.String,
            @SerialName("value") public val value: kotlin.String
        ) : CommandOptionChoice()

        @Serializable
        public data class Int(
            @SerialName("name") public val name: kotlin.String,
            @SerialName("value") public val value: kotlin.Int
        ) : CommandOptionChoice()

        @Serializable
        public data class Double(
            @SerialName("name") public val name: kotlin.String,
            @SerialName("value") public val value: kotlin.Double
        ) : CommandOptionChoice()
    }
}
