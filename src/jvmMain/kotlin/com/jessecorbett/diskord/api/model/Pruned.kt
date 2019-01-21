package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Pruned(
        @JsonProperty("pruned") val prunedCount: Int
)
