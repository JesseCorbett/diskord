package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.websocket.model.GatewayMessage

interface HeartbeatManager {
    fun start(heartbeatPeriod: Long, sendHeartbeat: () -> Unit, sendAcknowledgement: () -> Unit)

    fun acceptHeartbeat(message: GatewayMessage)

    fun acceptAcknowledgement(message: GatewayMessage)

    fun close()
}
