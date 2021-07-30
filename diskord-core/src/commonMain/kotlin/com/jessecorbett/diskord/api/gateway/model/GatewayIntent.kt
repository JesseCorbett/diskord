package com.jessecorbett.diskord.api.gateway.model

import com.jessecorbett.diskord.api.gateway.events.DiscordEvent
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * An enum defining all currently supported gateway intents.
 */
public enum class GatewayIntent(public val mask: Int, internal val privileged: Boolean = false) {
    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.GUILD_CREATE]
     * - [DiscordEvent.GUILD_UPDATE]
     * - [DiscordEvent.GUILD_DELETE]
     * - [DiscordEvent.GUILD_ROLE_CREATE]
     * - [DiscordEvent.GUILD_ROLE_UPDATE]
     * - [DiscordEvent.GUILD_ROLE_DELETE]
     * - [DiscordEvent.CHANNEL_CREATE]
     * - [DiscordEvent.CHANNEL_UPDATE]
     * - [DiscordEvent.CHANNEL_DELETE]
     * - [DiscordEvent.CHANNEL_PINS_UPDATE]
     * - [DiscordEvent.THREAD_CREATE]
     * - [DiscordEvent.THREAD_UPDATE]
     * - [DiscordEvent.THREAD_DELETE]
     * - [DiscordEvent.THREAD_LIST_SYNC]
     * - [DiscordEvent.THREAD_MEMBER_UPDATE]
     * - [DiscordEvent.THREAD_MEMBERS_UPDATE]
     */
    GUILDS(0x00000001),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.GUILD_MEMBER_ADD]
     * - [DiscordEvent.GUILD_MEMBER_UPDATE]
     * - [DiscordEvent.GUILD_MEMBER_REMOVE]
     * - [DiscordEvent.THREAD_MEMBERS_UPDATE]
     *
     * *Note: This intent is privileged and may require verification with Discord
     * (see [https://support.discord.com/hc/en-us/articles/360040720412-Bot-Verification-and-Data-Whitelisting]).*
     */
    GUILD_MEMBERS(0x00000002, true),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.GUILD_BAN_ADD]
     * - [DiscordEvent.GUILD_BAN_REMOVE]
     */
    GUILD_BANS(0x00000004),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.GUILD_EMOJIS_UPDATE]
     * - [DiscordEvent.GUILD_STICKERS_UPDATE]
     */
    // TODO - Rename to GUILD_EMOJIS_AND_STICKERS(?)
    GUILD_EMOJIS(0x00000008),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.GUILD_INTEGRATIONS_UPDATE]
     * - [DiscordEvent.INTEGRATION_CREATE]
     * - [DiscordEvent.INTEGRATION_UPDATE]
     * - [DiscordEvent.INTEGRATION_DELETE]
     */
    GUILD_INTEGRATIONS(0x00000010),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.WEBHOOKS_UPDATE]
     */
    GUILD_WEBHOOKS(0x00000020),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.INVITE_CREATE]
     * - [DiscordEvent.INVITE_DELETE]
     */
    GUILD_INVITES(0x00000040),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.VOICE_STATE_UPDATE]
     */
    GUILD_VOICE_STATES(0x00000080),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.PRESENCE_UPDATE]
     *
     * *Note: This intent is privileged and may require verification with Discord
     * (see [https://support.discord.com/hc/en-us/articles/360040720412-Bot-Verification-and-Data-Whitelisting]).*
     */
    GUILD_PRESENCES(0x00000100, true),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.MESSAGE_CREATE]
     * - [DiscordEvent.MESSAGE_UPDATE]
     * - [DiscordEvent.MESSAGE_DELETE]
     * - [DiscordEvent.MESSAGE_DELETE_BULK]
     */
    GUILD_MESSAGES(0x00000200),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.MESSAGE_REACTION_ADD]
     * - [DiscordEvent.MESSAGE_REACTION_REMOVE]
     * - [DiscordEvent.MESSAGE_REACTION_REMOVE_ALL]
     * - [DiscordEvent.MESSAGE_REACTION_REMOVE_EMOJI]
     */
    GUILD_MESSAGE_REACTIONS(0x00000400),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.TYPING_START] (Guilds)
     */
    GUILD_MESSAGE_TYPING(0x00000800),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.MESSAGE_CREATE]
     * - [DiscordEvent.MESSAGE_UPDATE]
     * - [DiscordEvent.MESSAGE_DELETE]
     * - [DiscordEvent.CHANNEL_PINS_UPDATE]
     */
    DIRECT_MESSAGES(0x00001000),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.MESSAGE_REACTION_ADD]
     * - [DiscordEvent.MESSAGE_REACTION_REMOVE]
     * - [DiscordEvent.MESSAGE_REACTION_REMOVE_ALL]
     * - [DiscordEvent.MESSAGE_REACTION_REMOVE_EMOJI]
     */
    DIRECT_MESSAGE_REACTIONS(0x00002000),

    /**
     * Subscribe to the following events:
     *
     * - [DiscordEvent.TYPING_START] (DMs)
     */
    DIRECT_MESSAGE_TYPING(0x00004000)
}

/**
 * An immutable collection of [GatewayIntent] values stored as a bitmask integer.  This is intended for use when
 * creating a Diskord bot instance to signal to the Discord API requested intents.
 *
 * FIXME: Update this example with updated DSL (or remove it completely)
 *
 * Example:
 * ```kotlin
 *     bot(apiKey, intents = GatewayIntents.of(GatewayIntents.GUILDS, GatewayIntents.GUILD_MEMBERS)) {
 *         guildUpdated {
 *             // do something
 *         }
 *         userJoinedGuild {
 *             // do something
 *         }
 *     }
 * ```
 *
 * *Note: All operations on [GatewayIntents] instances will create a new [GatewayIntents] instance.*
 */
@Serializable(with = GatewayIntentsSerializer::class)
public data class GatewayIntents(val value: Int) {
    public operator fun contains(intent: GatewayIntent): Boolean {
        return intent in value
    }

    public operator fun contains(permissions: GatewayIntents): Boolean {
        return value and permissions.value == permissions.value
    }

    public operator fun plus(intents: Int): GatewayIntents = GatewayIntents(value or intents)

    public operator fun plus(intents: GatewayIntents): GatewayIntents = plus(intents.value)

    public operator fun plus(intents: Collection<GatewayIntent>): Unit = intents.forEach { plus(it.mask) }

    public operator fun plus(intent: GatewayIntent): GatewayIntents = plus(intent.mask)

    public operator fun minus(intents: Int): GatewayIntents = GatewayIntents(value and intents.inv())

    public operator fun minus(intents: GatewayIntents): GatewayIntents = minus(intents.value)

    public operator fun minus(intents: Collection<GatewayIntent>): Unit = intents.forEach { minus(it.mask) }

    public operator fun minus(intent: GatewayIntent): GatewayIntents = minus(intent.mask)

    override fun toString(): String = "GatewayIntents($value) --> ${GatewayIntent.values().filter { it in value }.joinToString()}"

    public companion object {
        /**
         * A [GatewayIntents] object specifying all intents including privileged intents.
         */
        public val ALL: GatewayIntents = of(*GatewayIntent.values())

        /**
         * A [GatewayIntents] object specifying all intents excluding privileged intents.
         */
        public val NON_PRIVILEGED: GatewayIntents = of(GatewayIntent.values().filterNot { it.privileged })

        /**
         * A [GatewayIntents] object specifying no intents.
         */
        public val NONE: GatewayIntents = GatewayIntents(0)

        /**
         * Create a [GatewayIntents] object including all intents specified in the provided collection.
         */
        public fun of(intents: Collection<GatewayIntent>): GatewayIntents {
            return GatewayIntents(intents.map { intent -> intent.mask }.reduce { left, right -> left or right })
        }

        /**
         * Create a [GatewayIntents] object including all intents specified.
         */
        public fun of(vararg intents: GatewayIntent): GatewayIntents {
            return GatewayIntents(intents.map { intent -> intent.mask }.reduce { left, right -> left or right })
        }

        private operator fun Int.contains(intent: GatewayIntent) = this and intent.mask == intent.mask
    }
}

public object GatewayIntentsSerializer : KSerializer<GatewayIntents> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("GatewayIntents", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): GatewayIntents = GatewayIntents(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: GatewayIntents): Unit = encoder.encodeInt(value.value)
}
