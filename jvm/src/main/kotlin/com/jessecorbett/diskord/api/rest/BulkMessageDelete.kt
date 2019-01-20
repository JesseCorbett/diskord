package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class BulkMessageDelete(
        @JsonProperty("messages") val messages: List<String>
)
