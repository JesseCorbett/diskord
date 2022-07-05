package com.jessecorbett.diskord.api.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = UserStatusSerializer::class)
public enum class UserStatus(public val code: String) {
    UNKNOWN("-1"),
    ONLINE("online"),
    DO_NOT_DISTURB("dnd"),
    IDLE("idle"),
    INVISIBLE("invisible"),
    OFFLINE("offline")
}

public object UserStatusSerializer : KSerializer<UserStatus> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UserStatus", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UserStatus {
        val value = decoder.decodeString()
        return UserStatus.values().find { it.code == value } ?: UserStatus.UNKNOWN
    }

    override fun serialize(encoder: Encoder, value: UserStatus) {
        encoder.encodeString(value.code)
    }
}
