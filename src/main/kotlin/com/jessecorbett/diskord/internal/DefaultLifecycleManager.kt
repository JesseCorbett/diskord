package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.DiscordLifecycleManager
import com.jessecorbett.diskord.api.gateway.WebSocketCloseCode
import okhttp3.Response
import org.slf4j.LoggerFactory

class DefaultLifecycleManager : DiscordLifecycleManager {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private var restart = fun() {
        throw RuntimeException("Tried to restart DefaultLifecycleManager before calling start method")
    }

    override fun start(restart: () -> Unit) {
        this.restart = restart
    }

    override fun closing(code: WebSocketCloseCode, reason: String) {
        logger.info("Closing with code '$code' and reason '$reason'")
        if (code != WebSocketCloseCode.NORMAL_CLOSURE)
            restart()
    }

    override fun closed(code: WebSocketCloseCode, reason: String) {
        logger.info("Closed with code '$code' and reason '$reason'")
        if (code != WebSocketCloseCode.NORMAL_CLOSURE)
            restart()
    }

    override fun failed(failure: Throwable, response: Response?) {
        logger.error("Socket connection encountered an exception", failure)
        restart()
    }
}
