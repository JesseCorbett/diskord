package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateDM(
        @JsonProperty("recipient_id") val recipientId: String
)
