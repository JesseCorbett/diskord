package com.jessecorbett.diskord.api.websocket.commands

import com.fasterxml.jackson.annotation.JsonProperty

data class RequestGuildMembers(
        @JsonProperty("guild_id") val snowflakeId: String,
        @JsonProperty("query") val namePrefix: String = "",
        @JsonProperty("limit") val maxResults: Int = 0
)
