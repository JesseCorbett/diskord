package com.jessecorbett.diskord.api.gateway.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Resumed(@SerialName("_trace") val trace: List<String>)
