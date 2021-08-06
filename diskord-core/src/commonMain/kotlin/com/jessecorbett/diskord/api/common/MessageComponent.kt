package com.jessecorbett.diskord.api.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
public sealed class MessageComponent(
    @SerialName("type") public val type: Int
)

@Serializable
public data class ActionRow(
    @SerialName("components") public val components: List<MessageComponent>
) : MessageComponent(1)

@Serializable
public data class Button(
    @SerialName("custom_id") public val customId: String? = null,
    @SerialName("disabled") public val disabled: Boolean = false,
    @SerialName("style") public val style: ButtonStyle,
    @SerialName("label") public val label: String? = null,
    @SerialName("emoji") public val emoji: PartialEmoji? = null,
    @SerialName("url") public val url: String? = null,
) : MessageComponent(2)

@Serializable
public data class SelectMenu(
    @SerialName("custom_id") public val customId: String,
    @SerialName("disabled") public val disabled: Boolean = false,
    @SerialName("options") public val options: List<SelectOption>,
    @SerialName("placeholder") public val placeholder: String? = null,
    @SerialName("min_values") public val minValues: Int = 1,
    @SerialName("max_values") public val maxValues: Int = 1,
) : MessageComponent(3)

@Serializable(with = ButtonStyle.Serializer::class)
public sealed class ButtonStyle(public val code: Int) {
    public object Primary : ButtonStyle(1)
    public object Secondary : ButtonStyle(2)
    public object Success : ButtonStyle(3)
    public object Danger : ButtonStyle(4)
    public object Link : ButtonStyle(5)

    public class Other(code: Int) : ButtonStyle(code)

    internal object Serializer : KSerializer<ButtonStyle> {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("ButtonStyle", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): ButtonStyle {
            return when(val code = decoder.decodeInt()) {
                1 -> Primary
                2 -> Secondary
                3 -> Success
                4 -> Danger
                5 -> Link
                else -> Other(code)
            }
        }

        override fun serialize(encoder: Encoder, value: ButtonStyle) = encoder.encodeInt(value.code)

    }
}

@Serializable
public data class SelectOption(
    @SerialName("label") public val label: String,
    @SerialName("value") public val value: String,
    @SerialName("description") public val description: String? = null,
    @SerialName("emoji") public val emoji: PartialEmoji? = null,
    @SerialName("default") public val default: Boolean = false
)