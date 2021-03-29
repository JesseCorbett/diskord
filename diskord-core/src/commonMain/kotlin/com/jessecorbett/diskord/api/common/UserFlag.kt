package com.jessecorbett.diskord.api.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public enum class UserFlag(internal val mask: Int) {
    DISCORD_EMPLOYEE(1 shl 0),
    PARTNERED_SERVER_OWNER(1 shl 1),
    HYPESQUAD_EVENTS(1 shl 2),
    BUG_HUNTER_LEVEL_1(1 shl 3),
    HOUSE_BRAVERY(1 shl 6),
    HOUSE_BRILLIANCE(1 shl 7),
    HOUSE_BALANCE(1 shl 8),
    EARLY_SUPPORTER(1 shl 9),
    TEAM_USER(1 shl 10),
    SYSTEM(1 shl 12),
    BUG_HUNTER_LEVEL_2(1 shl 14),
    VERIFIED_BOT(1 shl 16),
    EARLY_VERIFIED_BOT_DEVELOPER(1 shl 17);
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
 *
 * *Note: All operations on a [UserFlags] instance will create a new [UserFlags] instance.*
 */
@Serializable(with = UserFlagsSerializer::class)
public data class UserFlags(val value: Int) {
    public operator fun contains(flag: UserFlag): Boolean {
        return flag in value
    }

    public operator fun contains(flags: UserFlags): Boolean {
        return value and flags.value == flags.value
    }

    public operator fun plus(flags: Int): UserFlags = UserFlags(value or flags)

    public operator fun plus(flags: UserFlags): UserFlags = plus(flags.value)

    public operator fun plus(flags: Collection<UserFlag>): Unit = flags.forEach { plus(it.mask) }

    public operator fun plus(flag: UserFlag): UserFlags = plus(flag.mask)

    public operator fun minus(flags: Int): UserFlags = UserFlags(value and flags.inv())

    public operator fun minus(flags: UserFlags): UserFlags = minus(flags.value)

    public operator fun minus(flags: Collection<UserFlag>): Unit = flags.forEach { minus(it.mask) }

    public operator fun minus(flag: UserFlag): UserFlags = minus(flag.mask)

    override fun toString(): String = "UserFlags($value) --> ${UserFlag.values().filter { it in value }.joinToString()}"

    public companion object {
        public val ALL: UserFlags = of(*UserFlag.values())

        public val NONE: UserFlags = UserFlags(0)

        public fun of(vararg flags: UserFlag): UserFlags {
            return UserFlags(flags.map { flag -> flag.mask }.reduce { left, right -> left or right })
        }

        private operator fun Int.contains(flag: UserFlag) = this and flag.mask == flag.mask
    }
}

public object UserFlagsSerializer : KSerializer<UserFlags> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UserFlags", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): UserFlags = UserFlags(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: UserFlags): Unit = encoder.encodeInt(value.value)
}
