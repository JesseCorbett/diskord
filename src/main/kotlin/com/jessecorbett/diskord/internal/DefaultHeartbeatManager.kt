package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.HeartbeatManager
import com.jessecorbett.diskord.api.gateway.GatewayMessage
import kotlinx.coroutines.experimental.*

private val threadPool = newSingleThreadContext("Heartbeat")

class DefaultHeartbeatManager : HeartbeatManager {
    private var heartbeatJob: Job? = null
    private lateinit var sendAcknowledgement: () -> Unit

    override fun start(heartbeatPeriod: Int, sendHeartbeat: () -> Unit, sendAcknowledgement: () -> Unit) {
        this.sendAcknowledgement = sendAcknowledgement
        runBlocking {
            try {
                heartbeatJob?.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        heartbeatJob = launch(threadPool) {
            while (true) {
                delay(heartbeatPeriod)
                sendHeartbeat()
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
