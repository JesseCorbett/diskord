package com.jessecorbett.diskord.api.common

import kotlinx.serialization.*

@Serializable
public data class User(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String,
    @SerialName("discriminator") val discriminator: Long,
    @SerialName("avatar") val avatarHash: String?,
    @SerialName("bot") val isBot: Boolean? = null,
    @SerialName("system") val isSystem: Boolean? = null,
    @SerialName("mfa_enabled") val twoFactorAuthEnabled: Boolean? = null,
    @SerialName("locale") val locale: String? = null,
    @SerialName("verified") val isVerified: Boolean? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("flags") val flags: UserFlags = UserFlags.NONE,
    @SerialName("premium_type") val premiumType: PremiumType? = null,
    @SerialName("public_flags") val publicFlags: UserFlags = UserFlags.NONE
)

@Serializable
public enum class PremiumType(public val code: Int) {
    @SerialName("0") NONE(0),
    @SerialName("1") NITRO_CLASSIC(1),
    @SerialName("2") NITRO(2)
}
