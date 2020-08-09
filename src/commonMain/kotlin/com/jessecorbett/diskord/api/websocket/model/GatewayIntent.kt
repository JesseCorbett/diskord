package com.jessecorbett.diskord.api.websocket.model

import kotlinx.serialization.*

enum class GatewayIntent(val mask: Int, val privileged: Boolean = false) {
    GUILDS(0x00000001),
    GUILD_MEMBERS(0x00000002),
    GUILD_BANS(0x00000004),
    GUILD_EMOJIS(0x00000008),
    GUILD_INTEGRATIONS(0x00000010),
    GUILD_WEBHOOKS(0x00000020),
    GUILD_INVITES(0x00000040),
    GUILD_VOICE_STATES(0x00000080),
    GUILD_PRESENCES(0x00000100, true),
    GUILD_MESSAGES(0x00000200, true),
    GUILD_MESSAGE_REACTIONS(0x00000400),
    GUILD_MESSAGE_TYPING(0x00000800),
    DIRECT_MESSAGES(0x00001000),
    DIRECT_MESSAGE_REACTIONS(0x00002000),
    DIRECT_MESSAGE_TYPING(0x00004000)
}

@Serializable(with = GatewayIntentsSerializer::class)
data class GatewayIntents(val value: Int) {
    operator fun contains(intent: GatewayIntent): Boolean {
        return intent in value
    }

    operator fun contains(permissions: GatewayIntents): Boolean {
        return value and permissions.value == permissions.value
    }

    operator fun plus(intents: Int) = GatewayIntents(value or intents)

    operator fun plus(intents: GatewayIntents) = plus(intents.value)

    operator fun plus(intents: Collection<GatewayIntent>) = intents.forEach { plus(it.mask) }

    operator fun plus(intent: GatewayIntent) = plus(intent.mask)

    operator fun minus(intents: Int) = GatewayIntents(value and intents.inv())

    operator fun minus(intents: GatewayIntents) = minus(intents.value)

    operator fun minus(intents: Collection<GatewayIntent>) = intents.forEach { minus(it.mask) }

    operator fun minus(intent: GatewayIntent) = minus(intent.mask)

    override fun toString() = "GatewayIntents($value) --> ${GatewayIntent.values().filter { it in value }.joinToString()}"

    companion object {
        val ALL = of(*GatewayIntent.values())

        val NON_PRIVILEGED = of(GatewayIntent.values().filterNot { it.privileged })

        val NONE = GatewayIntents(0)

        fun of(intents: Collection<GatewayIntent>) =
            GatewayIntents(intents.map { intent -> intent.mask }.reduce { left, right -> left or right })

        fun of(vararg intents: GatewayIntent) =
            GatewayIntents(intents.map { intent -> intent.mask }.reduce { left, right -> left or right })

        private operator fun Int.contains(intent: GatewayIntent) = this and intent.mask == intent.mask
    }
}

object GatewayIntentsSerializer : KSerializer<GatewayIntents> {
    override val descriptor: SerialDescriptor = PrimitiveDescriptor("GatewayIntents", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder) = GatewayIntents(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: GatewayIntents) = if (value.value > 0) {
        encoder.encodeInt(value.value)
    } else {
        encoder.encodeNull()
    }
}
