package com.jessecorbett.diskord.api.websocket.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Resumed(@SerialName("_trace") val trace: List<String>)
