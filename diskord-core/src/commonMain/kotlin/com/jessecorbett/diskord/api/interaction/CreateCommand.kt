package com.jessecorbett.diskord.api.interaction

import com.jessecorbett.diskord.api.common.Permissions
import com.jessecorbett.diskord.api.interaction.command.CommandOption
import com.jessecorbett.diskord.api.interaction.command.CommandType
import com.jessecorbett.diskord.internal.CodeEnum
import com.jessecorbett.diskord.internal.CodeEnumSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateCommand(
    @SerialName("name") val name: String,
    @SerialName("name_localizations") val localizedNames: Map<String, String>? = null,
    @SerialName("description") val description: String,
    @SerialName("description_localizations") val localizedDescriptions: Map<String, String>? = null,
    @SerialName("options") val options: List<CommandOption>? = null,
    @SerialName("default_member_permissions") val defaultPermission: Permissions? = null,
    @SerialName("dm_permission") val allowedInDms: Boolean = true,
    @SerialName("nsfw") val nsfw: Boolean = false,
    @SerialName("type") val type: CommandType = CommandType.ChatInput,
    @SerialName("integration_types") val integrationTypes: Set<CommandIntegrationType> = emptySet(),
)

@Serializable(with = CommandIntegrationTypeSerializer::class)
public enum class CommandIntegrationType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    GUILD(0),
    USER(1)
}

public class CommandIntegrationTypeSerializer : CodeEnumSerializer<CommandIntegrationType>(
    CommandIntegrationType.UNKNOWN,
    CommandIntegrationType.entries.toTypedArray()
)
