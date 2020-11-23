package com.jessecorbett.diskord.api.rest.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchGuildIntegration(
    @SerialName("expire_behavior") val expireBehavior: Int,
    @SerialName("expire_grace_period") val expirationGracePeriod: Int,
    @SerialName("enable_emoticons") val enableEmoticons: Boolean
)
