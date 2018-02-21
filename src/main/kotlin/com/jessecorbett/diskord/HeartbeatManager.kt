package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.gateway.GatewayMessage

interface HeartbeatManager {
    fun start(heartbeatPeriod: Int, sendHeartbeat: () -> Unit, sendAcknowledgement: () -> Unit)

    fun acceptHeartbeat(message: GatewayMessage)

    fun acceptAcknowledgement(message: GatewayMessage)

    fun close()
}
