package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Webhook(
        @JsonProperty("id") val id: String,
        @JsonProperty("guild_id") val guildId: String?,
        @JsonProperty("channel_id") val channelId: String,
        @JsonProperty("user") val user: User?,
        @JsonProperty("name") val defaultName: String?,
        @JsonProperty("avatar") val defaultAvatar: String?,
        @JsonProperty("token") val token: String
)
