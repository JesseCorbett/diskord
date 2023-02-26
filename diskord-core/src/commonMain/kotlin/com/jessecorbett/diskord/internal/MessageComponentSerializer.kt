package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.api.common.ActionRow
import com.jessecorbett.diskord.api.common.Button
import com.jessecorbett.diskord.api.common.ChannelSelectMenu
import com.jessecorbett.diskord.api.common.MentionableSelectMenu
import com.jessecorbett.diskord.api.common.MessageComponent
import com.jessecorbett.diskord.api.common.RoleSelectMenu
import com.jessecorbett.diskord.api.common.TextInput
import com.jessecorbett.diskord.api.common.TextSelectMenu
import com.jessecorbett.diskord.api.common.UserSelectMenu
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
