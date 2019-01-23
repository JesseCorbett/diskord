package com.jessecorbett.diskord.api.model

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
        @SerialName("id") val id: String,
        @SerialName("username") val username: String,
        @SerialName("discriminator") val discriminator: Long,
        @Optional @SerialName("avatar") val avatarHash: String? = null,
        @Optional @SerialName("bot") val isBot: Boolean = false,
        @Optional @SerialName("mfa_enabled") val twoFactorAuthEnabled: Boolean? = null,
        @Optional @SerialName("verified") val isVerified: Boolean? = null,
        @Optional @SerialName("email") val email: String? = null
)
