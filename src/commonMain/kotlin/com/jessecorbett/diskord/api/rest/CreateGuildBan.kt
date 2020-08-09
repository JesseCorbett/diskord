package com.jessecorbett.diskord.api.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateGuildBan(
    @SerialName("delete_message_days") val deleteMessageDays: Int?,
    @SerialName("reason") val reason: String?
)
