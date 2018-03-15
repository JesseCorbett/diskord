package com.jessecorbett.diskord.api.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.User
import java.time.Instant

data class GuildIntegration(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("type") val type: String,
        @JsonProperty("enabled") val enabled: Boolean,
        @JsonProperty("syncing") val syncing: Boolean,
        @JsonProperty("role_id") val roleId: String,
        @JsonProperty("expire_behavior") val expireBehavior: Int,
        @JsonProperty("expire_grace_period") val expirationGracePeriod: Int,
        @JsonProperty("user") val user: User,
        @JsonProperty("account") val account: IntegrationAccount,
        @JsonProperty("synced_at") val syncedAt: Instant
)

data class IntegrationAccount(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String
)
