package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class VoiceRegion(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("vip") val vip: Boolean,
    @SerialName("optimal") val optimal: Boolean,
    @SerialName("deprecated") val deprecated: Boolean,
    @SerialName("custom") val customRegion: Boolean
)
