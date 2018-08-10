package com.jessecorbett.diskord.api

import com.fasterxml.jackson.annotation.JsonProperty


class BearerToken(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("token_type") val tokenType: String,      // Should always be Bearer
        @JsonProperty("expires_in") val expiresInSeconds: Long,
        @JsonProperty("refresh_token") val refreshToken: String,
        @JsonProperty("scope") val scope: String
)
