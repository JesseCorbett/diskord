package com.jessecorbett.diskord.api.websocket.events

import com.fasterxml.jackson.annotation.JsonProperty

data class GuildIntegrationUpdate(@JsonProperty("guild_id") val guildId: String)