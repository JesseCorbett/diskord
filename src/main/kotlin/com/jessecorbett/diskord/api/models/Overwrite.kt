package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

data class Overwrite(
        @JsonProperty("id") val id: String,
        @JsonProperty("type") val type: OverwriteType,
        @JsonProperty("allow") val allowed: Int,// TODO: Parse int bit set
        @JsonProperty("deny") val denied: Int
)

enum class OverwriteType(@JsonValue val value: String) {
    ROLE("role"),
    MEMBER("member")
}
