package com.jessecorbett.diskord.api.interaction

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PatchCommand(
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("options") val options: List<CommandOption>? = null,
    @SerialName("default_permission") val defaultPermission: Boolean? = null,
)
