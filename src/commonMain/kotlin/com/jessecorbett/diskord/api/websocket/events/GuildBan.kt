package com.jessecorbett.diskord.api.websocket.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GuildBan(
        @SerialName("id") val id: String,
        @SerialName("username") val username: String,
        @SerialName("discriminator") val discriminator: String,
        @SerialName("avatar") val avatar: String?,
        @SerialName("bot") val isBot: Boolean = false,
        @SerialName("mfa_enabled") val twoFactorAuthEnabled: Boolean?,
        @SerialName("verified") val isVerified: Boolean?,
        @SerialName("email") val email: String?,
        @SerialName("guild_id") val guildId: String
)