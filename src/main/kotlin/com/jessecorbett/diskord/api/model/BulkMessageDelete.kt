package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class BulkMessageDelete(
        @JsonProperty("ids") val ids: List<String>,
        @JsonProperty("channel_id") val channelId: String
)
