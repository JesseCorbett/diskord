package com.jessecorbett.diskord.api.channel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BulkMessageDelete(@SerialName("messages") val messages: List<String>)
