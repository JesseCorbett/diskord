package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.api.common.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*

public object MessageComponentSerializer : JsonContentPolymorphicSerializer<MessageComponent>(MessageComponent::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<MessageComponent> {
        return when (val type = element.jsonObject["type"]?.jsonPrimitive?.intOrNull) {
            1 -> ActionRow.serializer()
            2 -> Button.serializer()
            3 -> TextSelectMenu.serializer()
            4 -> TextInput.serializer()
            5 -> UserSelectMenu.serializer()
            6 -> RoleSelectMenu.serializer()
            7 -> MentionableSelectMenu.serializer()
            8 -> ChannelSelectMenu.serializer()
            else -> error("Unknown MessageComponent $type")
        }
    }
}
