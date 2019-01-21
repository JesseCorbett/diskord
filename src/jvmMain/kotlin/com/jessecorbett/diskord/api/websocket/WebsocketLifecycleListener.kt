package com.jessecorbett.diskord.api.websocket

import okhttp3.Response

/**
 * An optional lifecycle listener of the lower level websocket connection.
 */
interface WebsocketLifecycleListener {

    /**
     * Runs when a websocket has started.
     */
    fun started()

    /**
     * Runs when a websocket is closing.
     *
     * @param code The close code.
     * @param reason The reason provided for the socket closing.
     */
    fun closing(code: WebSocketCloseCode, reason: String)

    /**
     * Runs when a websocket has closed.
     *
     * @param code The close code.
     * @param reason The reason provided for the socket closing.
     */
    fun closed(code: WebSocketCloseCode, reason: String)

    /**
     * Runs when a websocket connection has failed.
     *
     * @param failure The throwable source of the websocket failing.
     * @param response The response from the server if one exists.
     */
    fun failed(failure: Throwable, response: Response?)
}
