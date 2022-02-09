package com.jessecorbett.diskord.api.interaction.callback

import com.jessecorbett.diskord.api.channel.AllowedMentions
import com.jessecorbett.diskord.api.common.Embed
import com.jessecorbett.diskord.api.common.Message
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
public data class InteractionResponse(
    @SerialName("type") val type: InteractionCallbackType,
    @SerialName("data") val data: InteractionCommandCallbackData? = null
)

// https://discord.com/developers/docs/interactions/receiving-and-responding#interaction-response-object-interaction-callback-type
@Serializable
public enum class InteractionCallbackType {
    @SerialName("1")
    Pong,
    @SerialName("4")
    ChannelMessageWithSource,
    @SerialName("5")
    DeferredChannelMessageWithSource,
    @SerialName("6")
    DeferredUpdateMessage,
    @SerialName("7")
    UpdateMessage,
    @SerialName("8")
    ApplicationCommandAutocompleteResult
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
    EPHEMERAL(1 shl 6),

    /**
     * Do not include any embeds when serializing this message
     */
    SUPPRESS_EMBEDS(1 shl 2)
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
