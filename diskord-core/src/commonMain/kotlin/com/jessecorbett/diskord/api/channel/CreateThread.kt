package com.jessecorbett.diskord.api.channel

import com.jessecorbett.diskord.api.common.ChannelType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateThread(
    @SerialName("name") val name: String,
    @SerialName("auto_archive_duration") val autoArchiveDuration: Int? = null
)

@Serializable
public data class CreateThreadWithType(
    @SerialName("name") val name: String,
    @SerialName("type") val type: ChannelType,
    @SerialName("auto_archive_duration") val autoArchiveDuration: Int? = null,
)
