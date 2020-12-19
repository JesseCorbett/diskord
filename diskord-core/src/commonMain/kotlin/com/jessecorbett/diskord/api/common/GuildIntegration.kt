package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildIntegration(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("enabled") val enabled: Boolean,
    @SerialName("syncing") val syncing: Boolean,
    @SerialName("role_id") val roleId: String,
    @SerialName("enable_emoticons") val twitchEmojiSync: Boolean? = null,
    @SerialName("expire_behavior") val expireBehavior: IntegrationExpireBehavior,
    @SerialName("expire_grace_period") val expirationGracePeriod: Int,
    @SerialName("user") val user: User? = null,
    @SerialName("account") val account: IntegrationAccount,
    @SerialName("synced_at") val syncedAt: String? = null,
    @SerialName("subscriber_count") val subscriberCount: Int? = null,
    @SerialName("revoked") val revoked: Boolean? = null,
    @SerialName("application") val application: IntegrationApplication? = null
)

@Serializable
public data class IntegrationAccount(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)

@Serializable
public enum class IntegrationExpireBehavior {
    @SerialName("0") REMOVE_ROLE,
    @SerialName("1") KICK
}

@Serializable
public data class IntegrationApplication(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon") val iconHash: String?,
    @SerialName("description") val description: String,
    @SerialName("summary") val summary: String,
    @SerialName("bot") val botUser: User
)
