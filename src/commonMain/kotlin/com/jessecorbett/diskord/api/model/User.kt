package com.jessecorbett.diskord.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
        @SerialName("id") val id: String,
        @SerialName("username") val username: String,
        @SerialName("discriminator") val discriminator: Long,
        @SerialName("avatar") val avatarHash: String?,
        @SerialName("bot") val isBot: Boolean = false,
        @SerialName("mfa_enabled") val twoFactorAuthEnabled: Boolean?,
        @SerialName("verified") val isVerified: Boolean?,
        @SerialName("email") val email: String?
)
