package com.jessecorbett.diskord.api.rest.guild

import com.jessecorbett.diskord.api.common.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ban(
    @SerialName("reason") val reason: String? = null,
    @SerialName("user") val user: User
)
