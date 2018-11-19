package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.HeartbeatManager
import com.jessecorbett.diskord.api.gateway.GatewayMessage
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory

class DefaultHeartbeatManager : HeartbeatManager {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private var heartbeatJob: Job? = null
    private var sendAcknowledgement = fun() {
        throw RuntimeException("Tried to restart DefaultHeartbeatManager before calling start method")
    }

    override fun start(heartbeatPeriod: Long, sendHeartbeat: () -> Unit, sendAcknowledgement: () -> Unit) {
        this.sendAcknowledgement = sendAcknowledgement
        heartbeatJob = GlobalScope.launch(Dispatchers.Default) {
            while (this.isActive) {
                sendHeartbeat()
                delay(heartbeatPeriod)
            }
        }
    }

    override fun close() {
        logger.info("Closing")
        GlobalScope.launch(Dispatchers.Default) {
            heartbeatJob?.cancelAndJoin()
            logger.info("Closed")
        }
    }

    override fun acceptHeartbeat(message: GatewayMessage) {
        logger.debug("Received heartbeat")
        sendAcknowledgement()
    }

    override fun acceptAcknowledgement(message: GatewayMessage) {
        // This default class is not tracking heartbeat acks
    }
}
