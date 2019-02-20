package com.jessecorbett.diskord.api.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BearerToken(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,      // Should always be Bearer
    @SerialName("expires_in") val expiresInSeconds: Long,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("scope") val scope: String
)
