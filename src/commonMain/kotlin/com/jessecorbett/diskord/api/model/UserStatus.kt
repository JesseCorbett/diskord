package com.jessecorbett.diskord.api.model

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable(with = UserStatusSerializer::class)
enum class UserStatus(val code: String) {
    ONLINE("online"),
    DO_NOT_DISTURB("dnd"),
    IDLE("idle"),
    INVISIBLE("invisible"),
    OFFLINE("offline")
}

object UserStatusSerializer : KSerializer<UserStatus> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("UserStatusSerializer")

    override fun deserialize(decoder: Decoder): UserStatus {
        val target = decoder.decodeString()
        return UserStatus.values().first {
            it.code == target
        }
    }

    override fun serialize(encoder: Encoder, obj: UserStatus) {
        encoder.encodeString(obj.code)
    }
}
