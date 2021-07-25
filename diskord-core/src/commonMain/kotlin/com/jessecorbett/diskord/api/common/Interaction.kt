package com.jessecorbett.diskord.api.common

import com.jessecorbett.diskord.api.channel.AllowedMentions
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
public data class Interaction(
    @SerialName("id") val id: String,
    @SerialName("application_id") val application_id: String,
    @SerialName("type") val type: InteractionRequestType,
    @SerialName("data") val data: InteractionData?,
    @SerialName("guild_id") val guild_id: String?,
    @SerialName("channel_id") val channel_id: String?,
    @SerialName("member") val member: GuildMember?,
    @SerialName("user") val user: User?,
    @SerialName("token") val token: String,
    @SerialName("version") val version: Int,
    @SerialName("message") val message: Message?
)

@Serializable(with = InteractionRequestType.Serializer::class)
public sealed class InteractionRequestType(public val type: Int) {
    public object Ping : InteractionRequestType(1)
    public object ApplicationCommand : InteractionRequestType(2)
    public object MessageComponent : InteractionRequestType(3)
    public class Other(type: Int) : InteractionRequestType(type)

    internal object Serializer : KSerializer<InteractionRequestType> {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("InteractionRequestType", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): InteractionRequestType {
            return when(val type = decoder.decodeInt()) {
                1 -> Ping
                2 -> ApplicationCommand
                3 -> MessageComponent
                else -> Other(type)
            }
        }

        override fun serialize(encoder: Encoder, value: InteractionRequestType) {
            encoder.encodeInt(value.type)
        }
    }
}

@Serializable
public data class InteractionData(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("resolved") val resolved: InteractionDataResolved?,
    @SerialName("options") val options: List<InteractionDataOption>?,
    @SerialName("custom_id") val custom_id: String,
    @SerialName("component_type") val component_type: Int,
)

@Serializable
public data class InteractionDataOption(
    val name: String,
    val type: CommandOptionType,
    val value: Int? = null,
    val options: List<InteractionDataOption> = emptyList()
)

@Serializable
public data class InteractionDataResolved(
    @SerialName("users") val users: Map<String, User>?,
    @SerialName("members") val members: Map<String, PartialMember>?,
    @SerialName("roles") val roles: Map<String, Role>?,
    @SerialName("channels") val channels: Map<String, PartialChannel>?
)

@Serializable
public data class InteractionResponse(
    @SerialName("type") val type: InteractionCallbackType,
    @SerialName("data") val data: InteractionCommandCallbackData?
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
    @SerialName("content") val content: String = "",
    @SerialName("embeds") val embeds: List<Embed> = emptyList(),
    @SerialName("allowed_mentions") val allowed_mentions: AllowedMentions? = null,
    @SerialName("flags") val flags: Int? = null,
    @SerialName("components") val components: List<Message> = emptyList()
)

// This will need more work when more flags are added, but for now it's fine
@Serializable(with = InteractionCommandCallbackDataFlags.Serializer::class)
public sealed class InteractionCommandCallbackDataFlags(public val code: Int) {
    public object None : InteractionCommandCallbackDataFlags(0)
    public object Ephemeral : InteractionCommandCallbackDataFlags(1 shl 6)
    public class Other(code: Int) : InteractionCommandCallbackDataFlags(code)

    internal object Serializer : KSerializer<InteractionCommandCallbackDataFlags> {
        override val descriptor
            get() = PrimitiveSerialDescriptor("InteractionCommandCallbackDataFlags", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): InteractionCommandCallbackDataFlags {
            return when(val code = decoder.decodeInt()) {
                0 -> None
                (1 shl 6) -> Ephemeral
                else -> Other(code)
            }
        }

        override fun serialize(encoder: Encoder, value: InteractionCommandCallbackDataFlags) {
            encoder.encodeInt(value.code)
        }

    }
}

@Serializable
public data class MessageInteraction(
    @SerialName("id") val id: String,
    @SerialName("type") val type: InteractionRequestType,
    @SerialName("name") val name: String,
    @SerialName("user") val user: User
)