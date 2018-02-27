package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class PatchGuildIntegration(
        @JsonProperty("expire_behavior") val expireBehavior: Int,
        @JsonProperty("expire_grace_period") val expirationGracePeriod: Int,
        @JsonProperty("enable_emoticons") val enableEmoticons: Boolean
)
