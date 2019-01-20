package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VoiceRegion(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("vip") val vip: Boolean,
        @JsonProperty("optimal") val optimal: Boolean,
        @JsonProperty("deprecated") val deprecated: Boolean,
        @JsonProperty("custom") val customRegion: Boolean
)