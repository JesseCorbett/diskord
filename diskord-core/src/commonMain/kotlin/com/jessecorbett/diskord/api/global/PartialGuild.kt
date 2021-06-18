package com.jessecorbett.diskord.api.global

import com.jessecorbett.diskord.api.common.Permissions
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PartialGuild(
    @SerialName("owner") val userIsOwner: Boolean,
    @SerialName("permissions") val permissions: Permissions,
    @SerialName("icon") val iconHash: String?,
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)
