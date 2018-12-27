package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class BareUser(@JsonProperty("id") val id: String)
