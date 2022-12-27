package com.jessecorbett.diskord.api.interaction.command

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class CommandType {
    @SerialName("1")
    ChatInput,
    @SerialName("2")
    User,
    @SerialName("3")
    Message,
}
