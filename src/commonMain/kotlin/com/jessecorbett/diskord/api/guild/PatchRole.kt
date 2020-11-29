package com.jessecorbett.diskord.api.guild

import com.jessecorbett.diskord.api.common.Color
import com.jessecorbett.diskord.api.common.Permissions
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PatchRole(
    @SerialName("name") val name: String,
    @SerialName("permissions") val permissions: Permissions,
    @SerialName("color") val color: Color,
    @SerialName("hoist") val displayedSeparately: Boolean,
    @SerialName("mentionable") val mentionable: Boolean
)
