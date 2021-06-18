package com.jessecorbett.diskord.api.global

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateDM(@SerialName("recipient_id") val recipientId: String)
