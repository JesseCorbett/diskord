package com.jessecorbett.diskord.api.interaction

import com.jessecorbett.diskord.api.channel.AllowedMentions
import com.jessecorbett.diskord.api.common.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
public data class Interaction(
    @SerialName("id") val id: String,
    @SerialName("application_id") val applicationId: String,
    @SerialName("type") val type: InteractionType,
    @SerialName("data") val data: CommandInteractionData? = null,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("channel_id") val channelId: String? = null,
    @SerialName("member") val member: GuildMember? = null,
    @SerialName("user") val user: User? = null,
    @SerialName("token") val token: String,
    @SerialName("version") val version: Int,
    @SerialName("message") val message: Message? = null
)

@Serializable(with = InteractionType.Serializer::class)
public sealed class InteractionType(public val type: Int) {
    public object Ping : InteractionType(1)
    public object ApplicationCommand : InteractionType(2)
    public object MessageComponent : InteractionType(3)
    public class Other(type: Int) : InteractionType(type)

    internal object Serializer : KSerializer<InteractionType> {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("InteractionRequestType", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): InteractionType {
            return when(val type = decoder.decodeInt()) {
                1 -> Ping
                2 -> ApplicationCommand
                3 -> MessageComponent
                else -> Other(type)
            }
        }

        override fun serialize(encoder: Encoder, value: InteractionType) {
            encoder.encodeInt(value.type)
        }
    }
}

@Serializable
public data class CommandInteractionData(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("resolved") val resolved: CommandInteractionDataResolved? = null,
    @SerialName("options") val options: List<CommandInteractionDataOption> = emptyList(),
    @SerialName("custom_id") val customId: String,
    @SerialName("component_type") val componentType: Int,
)

@Serializable
public data class CommandInteractionDataResolved(
    @SerialName("users") val users: Map<String, User> = emptyMap(),
    @SerialName("members") val members: Map<String, PartialMember> = emptyMap(),
    @SerialName("roles") val roles: Map<String, Role> = emptyMap(),
    @SerialName("channels") val channels: Map<String, PartialChannel> = emptyMap()
)

@Serializable
public data class CommandInteractionDataOption(
    @SerialName("name") val name: String,
    @SerialName("type") val type: CommandOptionType,
    @SerialName("value") val value: CommandOptionType? = null,
    @SerialName("options") val options: List<CommandInteractionDataOption> = emptyList()
)

@Serializable
public data class InteractionResponse(
    @SerialName("type") val type: InteractionCallbackType,
    @SerialName("data") val data: InteractionCommandCallbackData? = null
)

// https://discord.com/developers/docs/interactions/slash-commands#interaction-response-object-interaction-callback-type
@Serializable(with = InteractionCallbackType.Serializer::class)
public sealed class InteractionCallbackType(public val code: Int) {
    public object Pong : InteractionCallbackType(1)
    public object ChannelMessageWithSource : InteractionCallbackType(4)
    public object DeferredChannelMessageWithSource : InteractionCallbackType(5)
    public object DeferredUpdateMessage : InteractionCallbackType(6)
    public object UpdateMessage : InteractionCallbackType(7)
    public class Other(code: Int) : InteractionCallbackType(code)

    internal object Serializer : KSerializer<InteractionCallbackType> {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("InterationCallbackType", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): InteractionCallbackType {
            return when(val code = decoder.decodeInt()) {
                1 -> Pong
                4 -> ChannelMessageWithSource
                5 -> DeferredChannelMessageWithSource
                6 -> DeferredUpdateMessage
                7 -> UpdateMessage
                else -> Other(code)
            }
        }

        override fun serialize(encoder: Encoder, value: InteractionCallbackType) {
            encoder.encodeInt(value.code)
        }

    }
}

@Serializable
public data class InteractionCommandCallbackData(
    @SerialName("tts") val tts: Boolean = false,
    @SerialName("content") val content: String? = null,
    @SerialName("embeds") val embeds: List<Embed> = emptyList(),
    @SerialName("allowed_mentions") val allowedMentions: AllowedMentions? = null,
    @SerialName("flags") val flags: InteractionCommandCallbackDataFlags = InteractionCommandCallbackDataFlags.NONE,
    @SerialName("components") val components: List<Message> = emptyList()
)

public enum class InteractionCommandCallbackDataFlag(internal val mask: Int) {
    /**
     * Only the use receiving the message can see it
     */
    EPHEMERAL(0x40);
}

private typealias Flag = InteractionCommandCallbackDataFlag
private typealias Flags = InteractionCommandCallbackDataFlags
private typealias FlagsSerializer = InteractionCommandCallbackDataFlagsSerializer

@Serializable(with = FlagsSerializer::class)
public data class InteractionCommandCallbackDataFlags(val value: Int) {
    public operator fun contains(flag: Flag): Boolean = flag in value

    public operator fun contains(flags: Flags): Boolean = value and flags.value == flags.value


    public operator fun plus(flags: Int): Flags = Flags(value or flags)

    public operator fun plus(flags: Flags): Flags = plus(flags.value)

    public operator fun plus(flags: Collection<Flag>): Unit = flags.forEach { plus(it.mask) }

    public operator fun plus(flag: Flag): Flags = plus(flag.mask)

    public operator fun minus(flags: Int): Flags = Flags(value and flags.inv())

    public operator fun minus(flags: Flags): Flags = minus(flags.value)

    public operator fun minus(flags: Collection<Flag>): Unit = flags.forEach { minus(it.mask) }

    public operator fun minus(flag: Flag): Flags = minus(flag.mask)

    override fun toString(): String = "InteractionCommandCallbackDataFlags($value) --> ${Flag.values().filter { it in value }.joinToString()}"

    public companion object {
        public val ALL: Flags = of(*Flag.values())

        public val NONE: Flags = Flags(0)

        public fun of(vararg flags: Flag): Flags {
            return Flags(flags.map { flag -> flag.mask }.reduce { left, right -> left or right })
        }

        private operator fun Int.contains(flag: Flag) = this and flag.mask == flag.mask
    }
}

public object InteractionCommandCallbackDataFlagsSerializer : KSerializer<Flags> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("InteractionCommandCallbackDataFlags", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Flags = Flags(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: Flags): Unit = encoder.encodeInt(value.value)
}
