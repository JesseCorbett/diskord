package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class BulkMessageDelete(
        @JsonProperty("ids") val ids: List<String>,
        @JsonProperty("channel_id") val channelId: String
)
