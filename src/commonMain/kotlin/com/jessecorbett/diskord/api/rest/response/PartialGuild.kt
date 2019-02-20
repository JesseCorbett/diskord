package com.jessecorbett.diskord.api.rest.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartialGuild(
    @SerialName("owner") val userIsOwner: Boolean,
    @SerialName("permissions") val permissions: Int,
    @SerialName("icon") val iconHash: String?,
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)
