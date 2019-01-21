package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
        @JsonProperty("id") val id: String,
        @JsonProperty("username") val username: String,
        @JsonProperty("discriminator") val discriminator: Long,
        @JsonProperty("avatar") val avatarHash: String?,
        @JsonProperty("bot") val isBot: Boolean = false,
        @JsonProperty("mfa_enabled") val twoFactorAuthEnabled: Boolean?,
        @JsonProperty("verified") val isVerified: Boolean?,
        @JsonProperty("email") val email: String?
)
