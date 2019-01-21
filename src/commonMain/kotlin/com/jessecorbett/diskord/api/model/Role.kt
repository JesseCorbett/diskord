package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Role(
        @SerialName("id") val id: String,
        @SerialName("name") val name: String,
        @SerialName("color") val color: Color,
        @SerialName("hoist") val isUserListPinned: Boolean,
        @SerialName("position") val position: Int,
        @SerialName("permissions") val permissions: Int,
        @SerialName("manager") val isManagedByIntegration: Boolean,
        @SerialName("mentionable") val isMentionable: Boolean
)
