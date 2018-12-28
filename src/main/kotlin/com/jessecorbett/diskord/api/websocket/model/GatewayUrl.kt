package com.jessecorbett.diskord.api.websocket.model

data class GatewayUrl(val url: String)

data class GatewayBotUrl(val url: String, val shards: Int)
