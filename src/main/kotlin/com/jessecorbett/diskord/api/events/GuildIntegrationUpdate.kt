package com.jessecorbett.diskord.api.events

import com.fasterxml.jackson.annotation.JsonProperty

data class GuildIntegrationUpdate(@JsonProperty("guild_id") val guildId: String)