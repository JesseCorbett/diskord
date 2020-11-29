package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Role(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("color") val color: Color,
    @SerialName("hoist") val isUserListPinned: Boolean,
    @SerialName("position") val position: Int,
    @SerialName("permissions") val permissions: Permissions,
    @SerialName("managed") val isManagedByIntegration: Boolean,
    @SerialName("mentionable") val isMentionable: Boolean
)
