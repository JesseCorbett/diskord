package com.jessecorbett.diskord.api.interaction.command

import com.jessecorbett.diskord.api.common.Permissions
import com.jessecorbett.diskord.internal.CodeEnum
import com.jessecorbett.diskord.internal.CodeEnumSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://discord.com/developers/docs/interactions/application-commands#application-command-object
@Serializable
public data class Command(
    @SerialName("id") val id: String,
    @SerialName("type") val type: CommandType = CommandType.ChatInput,
    @SerialName("application_id") val applicationId: String,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    // https://discord.com/developers/docs/reference#locales
    @SerialName("description_localizations") val localizedDescriptions: Map<String, String> = emptyMap(),
    @SerialName("options") val options: List<CommandOption> = emptyList(),
    @SerialName("default_member_permissions") val defaultMemberPermissions: Permissions? = null,
    @SerialName("dm_permission") val availableInDMs: Boolean = true,
    @SerialName("nsfw") val nsfw: Boolean = false,
    @SerialName("version") val version: Long
)

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

@Serializable(with = CommandPermissionsTypeSerializer::class)
public enum class CommandPermissionsType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    Role(1),
    User(2)
}

public class CommandPermissionsTypeSerializer : CodeEnumSerializer<CommandPermissionsType>(CommandPermissionsType.UNKNOWN, CommandPermissionsType.values())

