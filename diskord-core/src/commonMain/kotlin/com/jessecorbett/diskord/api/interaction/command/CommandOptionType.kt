package com.jessecorbett.diskord.api.interaction.command

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class CommandOptionType {
    @SerialName("1")
    SubCommand,
    @SerialName("2")
    SubCommandGroup,
    @SerialName("3")
    String,
    @SerialName("4")
    Integer,
    @SerialName("5")
    Boolean,
    @SerialName("6")
    User,
    @SerialName("7")
    Channel,
    @SerialName("8")
    Role,
    @SerialName("9")
    Mentionable,
    @SerialName("10")
    Number
}
