package com.jessecorbett.diskord.api.events

import com.fasterxml.jackson.annotation.JsonProperty

class GuildBan(
        @JsonProperty("id") val id: String,
        @JsonProperty("username") val username: String,
        @JsonProperty("discriminator") val discriminator: String,
        @JsonProperty("avatar") val avatar: String?,
        @JsonProperty("bot") val isBot: Boolean = false,
        @JsonProperty("mfa_enabled") val twoFactorAuthEnabled: Boolean?,
        @JsonProperty("verified") val isVerified: Boolean?,
        @JsonProperty("email") val email: String?,
        @JsonProperty("guild_id") val guildId: String
)