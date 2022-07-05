package com.jessecorbett.diskord.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public interface CodeEnum {
    public val code: Int
}

public abstract class CodeEnumSerializer<T: CodeEnum>(private val unknown: T, private val values: Array<T>) : KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(unknown::class.simpleName!!, PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): T {
        val value = decoder.decodeInt()
        return values.find { it.code == value } ?: unknown
    }

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeInt(value.code)
    }
}
