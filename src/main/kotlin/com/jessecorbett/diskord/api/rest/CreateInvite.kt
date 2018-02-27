package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateInvite(
        @JsonProperty("max_age") val expiresInSeconds: Int,
        @JsonProperty("max_uses") val maxUses: Int,
        @JsonProperty("temporary") val temporaryMembership: Boolean,
        @JsonProperty("unique") val dontAttemptReuse: Boolean
)
