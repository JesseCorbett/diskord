package com.jessecorbett.diskord.api.common

import kotlinx.serialization.*

@Serializable
public data class User(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String,
    @SerialName("discriminator") val discriminator: Long,
    @SerialName("avatar") val avatarHash: String?,
    @SerialName("bot") val isBot: Boolean = false,
    @SerialName("mfa_enabled") val twoFactorAuthEnabled: Boolean? = null,
    @SerialName("locale") val locale: String? = null,
    @SerialName("verified") val isVerified: Boolean? = null,
    @SerialName("email") val email: String? = null
//    @SerialName("flags") val flags: Int = 0, // TODO: https://discordapp.com/developers/docs/resources/user#user-object-user-flags
//    @SerialName("premium_type") val premiumType: PremiumType? = null
)

@Serializable
public enum class UserFlags(public val code: Int) {
    @SerialName("0") NONE(0),
    @SerialName("4") HYPESQUAD_EVENTS(4),
    @SerialName("64") HOUSE_BRAVERY(64),
    @SerialName("128") HOUSE_BRILLIANCE(128),
    @SerialName("256") HOUSE_BALANCE(256)
}

@Serializable
public enum class PremiumType(public val code: Int) {
    @SerialName("0") NONE(0),
    @SerialName("1") NITRO_CLASSIC(1),
    @SerialName("2") NITRO(2)
}
