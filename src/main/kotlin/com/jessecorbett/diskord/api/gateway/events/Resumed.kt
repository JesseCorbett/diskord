package com.jessecorbett.diskord.api.gateway.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.Channel
import com.jessecorbett.diskord.api.User

data class Resumed(@JsonProperty("_trace") val trace: List<String>)
