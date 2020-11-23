package com.jessecorbett.diskord.api.rest.global

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateDM(@SerialName("recipient_id") val recipientId: String)
