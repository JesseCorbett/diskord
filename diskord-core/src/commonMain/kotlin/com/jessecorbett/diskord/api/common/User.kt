package com.jessecorbett.diskord.api.common

import com.jessecorbett.diskord.internal.CodeEnum
import com.jessecorbett.diskord.internal.CodeEnumSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class User(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String,
    @SerialName("discriminator") val discriminator: Long,
    @SerialName("global_name") val displayName: String? = null,
    @SerialName("avatar") val avatarHash: String?,
    @SerialName("bot") val isBot: Boolean? = null,
    @SerialName("system") val isSystem: Boolean? = null,
    @SerialName("mfa_enabled") val twoFactorAuthEnabled: Boolean? = null,
    @SerialName("banner") val bannerHash: String? = null,
    @SerialName("accent_color") val accentColor: Color? = null,
    @SerialName("locale") val locale: String? = null,
    @SerialName("verified") val isVerified: Boolean? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("flags") val flags: UserFlags = UserFlags.NONE,
    @SerialName("premium_type") val premiumType: PremiumType? = null,
    @SerialName("public_flags") val publicFlags: UserFlags = UserFlags.NONE,
    @SerialName("avatar_decorations") val avatarDecorations: String? = null,
)

@Serializable(with = PremiumTypeSerializer::class)
public enum class PremiumType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    NONE(0),
    NITRO_CLASSIC(1),
    NITRO(2)
}

public class PremiumTypeSerializer : CodeEnumSerializer<PremiumType>(PremiumType.UNKNOWN, PremiumType.values())
