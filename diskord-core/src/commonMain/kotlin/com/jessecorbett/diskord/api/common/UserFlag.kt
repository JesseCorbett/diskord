package com.jessecorbett.diskord.api.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public enum class UserFlag(internal val mask: Int) {
    DISCORD_EMPLOYEE(0x00000001),
    PARTNERED_SERVER_OWNER(0x00000002),
    HYPESQUAD_EVENTS(0x00000004),
    BUG_HUNTER_LEVEL_1(0x00000008),
    HOUSE_BRAVERY(0x00000040),
    HOUSE_BRILLIANCE(0x00000080),
    HOUSE_BALANCE(0x00000100),
    EARLY_SUPPORTER(0x00000200),
    TEAM_USER(0x00000400),
    SYSTEM(0x00001000),
    BUG_HUNTER_LEVEL_2(0x00004000),
    VERIFIED_BOT(0x00010000),
    EARLY_VERIFIED_BOT_DEVELOPER(0x00020000);
}

/**
 * An immutable collection of [UserFlag] values stored as a bitmask integer.
 * This can be used to determine which flags a user has.
 *
 * Example:
 * ```kotlin
 *     if (UserFlag.EARLY_SUPPORTER in user.publicFlags) {
 *         // do something
 *     }
 * ```
 */
@Serializable(with = UserFlagsSerializer::class)
public data class UserFlags(val value: Int) {
    public operator fun contains(flag: UserFlag): Boolean {
        return flag in value
    }

    public operator fun contains(flags: UserFlags): Boolean {
        return value and flags.value == flags.value
    }

    override fun toString(): String = "UserFlags($value) --> ${UserFlag.values().filter { it in value }.joinToString()}"

    public companion object {
        public val NONE: UserFlags = UserFlags(0)

        private operator fun Int.contains(flag: UserFlag) = this and flag.mask == flag.mask
    }
}

public object UserFlagsSerializer : KSerializer<UserFlags> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UserFlags", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): UserFlags = UserFlags(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: UserFlags): Unit = encoder.encodeInt(value.value)
}
