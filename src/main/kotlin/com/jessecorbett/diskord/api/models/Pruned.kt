package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Pruned(
        @JsonProperty("pruned") val prunedCount: Int
)
