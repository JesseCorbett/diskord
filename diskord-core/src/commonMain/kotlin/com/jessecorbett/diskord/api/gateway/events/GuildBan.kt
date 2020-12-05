package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public class GuildBan(
    @SerialName("guild_id") public val guildId: String,
    @SerialName("user") public val user: User
)
