package com.jessecorbett.diskord.api.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public enum class SystemChannelFlag(internal val mask: Int) {
    /**
     * Suppress member join notifications.
     */
    SUPPRESS_JOIN_NOTIFICATIONS(0x00000001),

    /**
     * Suppress server boost notifications.
     */
    SUPPRESS_PREMIUM_SUBSCRIPTIONS(0x00000002)
}

/**
 * An immutable collection of [SystemChannelFlag] values stored as a bitmask integer.
 * This can be used to determine which flags the system channel in a guild contains.
 *
 * Example:
 * ```kotlin
 *     if (SystemChannelFlag.SUPPRESS_JOIN_NOTIFICATIONS in guild.systemChannelFlags) {
 *         // do something
 *     }
 * ```
 */
@Serializable(with = SystemChannelFlagsSerializer::class)
public data class SystemChannelFlags(val value: Int) {
    public operator fun contains(flag: SystemChannelFlag): Boolean {
        return flag in value
    }

    public operator fun contains(flags: SystemChannelFlags): Boolean {
        return value and flags.value == flags.value
    }

    public operator fun plus(flags: Int): SystemChannelFlags = SystemChannelFlags(value or flags)

    public operator fun plus(flags: SystemChannelFlags): SystemChannelFlags = plus(flags.value)

    public operator fun plus(flags: Collection<SystemChannelFlag>): Unit = flags.forEach { plus(it.mask) }

    public operator fun plus(flag: SystemChannelFlag): SystemChannelFlags = plus(flag.mask)

    public operator fun minus(flags: Int): SystemChannelFlags = SystemChannelFlags(value and flags.inv())

    public operator fun minus(flags: SystemChannelFlags): SystemChannelFlags = minus(flags.value)

    public operator fun minus(flags: Collection<SystemChannelFlag>): Unit = flags.forEach { minus(it.mask) }

    public operator fun minus(flag: SystemChannelFlag): SystemChannelFlags = minus(flag.mask)

    override fun toString(): String = "SystemChannelFlags($value) --> ${SystemChannelFlag.values().filter { it in value }.joinToString()}"

    public companion object {
        public val ALL: SystemChannelFlags = of(*SystemChannelFlag.values())

        public val NONE: SystemChannelFlags = SystemChannelFlags(0)

        public fun of(vararg flags: SystemChannelFlag): SystemChannelFlags {
            return SystemChannelFlags(flags.map { flag -> flag.mask }.reduce { left, right -> left or right })
        }

        private operator fun Int.contains(flag: SystemChannelFlag) = this and flag.mask == flag.mask
    }
}

public object SystemChannelFlagsSerializer : KSerializer<SystemChannelFlags> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UserFlags", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): SystemChannelFlags = SystemChannelFlags(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: SystemChannelFlags): Unit = encoder.encodeInt(value.value)
}
