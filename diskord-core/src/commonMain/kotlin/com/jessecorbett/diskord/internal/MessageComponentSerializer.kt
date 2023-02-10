package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.api.common.ActionRow
import com.jessecorbett.diskord.api.common.Button
import com.jessecorbett.diskord.api.common.MessageComponent
import com.jessecorbett.diskord.api.common.SelectMenu
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

public object MessageComponentSerializer : JsonContentPolymorphicSerializer<MessageComponent>(MessageComponent::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out MessageComponent> {
        return when (val type = element.jsonObject["type"]?.jsonPrimitive?.intOrNull) {
            1 -> ActionRow.serializer()
            2 -> Button.serializer()
            3 -> SelectMenu.serializer()
            else -> error("Unknown MessageComponent $type")
        }
    }
}
