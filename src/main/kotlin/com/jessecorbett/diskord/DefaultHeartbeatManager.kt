package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.gateway.GatewayMessage
import kotlinx.coroutines.experimental.*

private val threadPool = newSingleThreadContext("Heartbeat")

class DefaultHeartbeatManager : HeartbeatManager {
    private var heartbeatJob: Job? = null
    private lateinit var sendAcknowledgement: () -> Unit

    override fun start(heartbeatPeriod: Int, sendHeartbeat: () -> Unit, sendAcknowledgement: () -> Unit) {
        this.sendAcknowledgement = sendAcknowledgement
        heartbeatJob = launch(threadPool) {
            while (true) {
                sendHeartbeat()
                delay(heartbeatPeriod)
            }
        }
    }

    override fun close() {
        runBlocking {
            heartbeatJob?.join()
        }
    }

    override fun acceptHeartbeat(message: GatewayMessage) {
        println(message)
        sendAcknowledgement()
    }

    override fun acceptAcknowledgement(message: GatewayMessage) {

    }
}
