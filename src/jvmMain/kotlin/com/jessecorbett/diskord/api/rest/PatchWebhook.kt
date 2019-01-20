package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class PatchWebhook(
        @JsonProperty("name") val name: String? = null,
        @JsonProperty("avatar") val base64AvatarData: String? = null,
        @JsonProperty("channel_id") val channelId: String? = null
)
