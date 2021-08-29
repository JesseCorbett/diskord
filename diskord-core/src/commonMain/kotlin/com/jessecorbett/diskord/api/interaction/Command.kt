package com.jessecorbett.diskord.api.interaction

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

// https://discord.com/developers/docs/interactions/application-commands#application-command-object
@Serializable
public data class Command(
    @SerialName("id") val id: String,
    @SerialName("type") val type: CommandType = CommandType.ChatInput,
    @SerialName("application_id") val applicationId: String,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("options") val options: List<CommandOption> = emptyList(),
    @SerialName("default_permission") val defaultPermission: Boolean = true
)

@Serializable(with = CommandType.Serializer::class)
public sealed class CommandType(public val type: Int) {
    public object ChatInput : CommandType(1)
    public object User : CommandType(2)
    public object Message : CommandType(3)
    public class Other(type: Int) : CommandType(type)

    internal object Serializer : KSerializer<CommandType> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("CommandType", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): CommandType {
            return when(val type = decoder.decodeInt()) {
                1 -> ChatInput
                2 -> User
                3 -> Message
                else -> Other(type)
            }
        }

        override fun serialize(encoder: Encoder, value: CommandType) {
            encoder.encodeInt(value.type)
        }
    }
}

@Serializable
public data class CommandOption(
    @SerialName("type") val type: CommandOptionType,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("required") val required: Boolean = false,
    @SerialName("choices") val choices: List<CommandOptionChoice> = emptyList(),
    @SerialName("options") val options: List<CommandOption> = emptyList()
)

@Serializable(with = CommandOptionType.Serializer::class)
public sealed class CommandOptionType(public val type: Int) {
    public object SubCommand : CommandOptionType(1)
    public object SubCommandGroup : CommandOptionType(2)
    public object String : CommandOptionType(3)
    public object Integer : CommandOptionType(4)
    public object Boolean : CommandOptionType(5)
    public object User : CommandOptionType(6)
    public object Channel : CommandOptionType(7)
    public object Role : CommandOptionType(8)
    public object Mentionable : CommandOptionType(9)
    public object Number : CommandOptionType(10)
    public class Other(type: Int) : CommandOptionType(type)

    internal object Serializer : KSerializer<CommandOptionType> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("CommandOptionType", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): CommandOptionType {
            return when(val type = decoder.decodeInt()) {
                1 -> SubCommand
                2 -> SubCommandGroup
                3 -> String
                4 -> Integer
                5 -> Boolean
                6 -> User
                7 -> Channel
                8 -> Role
                9 -> Mentionable
                10 -> Number
                else -> Other(type)
            }
        }

        override fun serialize(encoder: Encoder, value: CommandOptionType) {
            encoder.encodeInt(value.type)
        }
    }
}

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

// https://discord.com/developers/docs/interactions/slash-commands#application-command-permissions-object-guild-application-command-permissions-structure
@Serializable
public data class GuildCommandPermissions(
    @SerialName("id") val id: String,
    @SerialName("application_id") val applicationId: String,
    @SerialName("guild_id") val guildId: String,
    @SerialName("permissions") val permissions: List<CommandPermissions>
)

@Serializable
public data class PartialGuildCommandPermissions(
    @SerialName("id") val id: String,
    @SerialName("permissions") val permissions: List<CommandPermissions>
)

@Serializable
public data class CommandPermissions(
    @SerialName("id") val id: String,
    @SerialName("type") val type: CommandPermissionsType,
    @SerialName("permission") val permission: Boolean
)

@Serializable(with = CommandPermissionsType.Serializer::class)
public sealed class CommandPermissionsType(public val code: Int) {
    public object Role : CommandPermissionsType(1)
    public object User : CommandPermissionsType(2)
    public class Other(code: Int) : CommandPermissionsType(code)

    internal object Serializer : KSerializer<CommandPermissionsType> {

        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("CommandPermissionsType", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): CommandPermissionsType {
            return when(val code = decoder.decodeInt()) {
                1 -> Role
                2 -> User
                else -> Other(code)
            }
        }

        override fun serialize(encoder: Encoder, value: CommandPermissionsType) {
            encoder.encodeInt(value.code)
        }
    }
}
