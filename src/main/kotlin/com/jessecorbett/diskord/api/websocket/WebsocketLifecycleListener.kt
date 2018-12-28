package com.jessecorbett.diskord.api.websocket

import okhttp3.Response

interface WebsocketLifecycleListener {
    fun started() {

    }

    fun closing(code: WebSocketCloseCode, reason: String) {

    }

    fun closed(code: WebSocketCloseCode, reason: String) {

    }

    fun failed(failure: Throwable, response: Response?) {

    }
}
