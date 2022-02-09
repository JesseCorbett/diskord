package com.jessecorbett.diskord.api.interaction

import com.jessecorbett.diskord.api.interaction.command.CommandOption
import com.jessecorbett.diskord.api.interaction.command.CommandType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateCommand(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("options") val options: List<CommandOption>? = emptyList(),
    @SerialName("default_permission") val defaultPermission: Boolean = true,
    @SerialName("type") val type: CommandType = CommandType.ChatInput,
)
