package com.jessecorbett.diskord.api.websocket.events

import com.fasterxml.jackson.annotation.JsonProperty

data class Resumed(@JsonProperty("_trace") val trace: List<String>)
