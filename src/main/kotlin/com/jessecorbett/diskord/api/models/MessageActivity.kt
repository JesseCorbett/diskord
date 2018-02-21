package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageActivity(
        @JsonProperty("type") val type: MessageActivityType,
        @JsonProperty("party_id") val partyId: String
)

enum class MessageActivityType(val code: Int) {
    JOIN(0),
    SPECTATE(1),
    LISTEN(2),
    JOIN_REQUEST(3)
}
