package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.DiscordLifecycleManager
import okhttp3.Response
import java.io.EOFException
import java.net.SocketException
import java.net.SocketTimeoutException

class DefaultLifecycleManager : DiscordLifecycleManager {
    private lateinit var restart: () -> Unit

    override fun start(restart: () -> Unit) {
        this.restart = restart
    }

    override fun closing(code: Int, reason: String) {
        println(code)
        println(reason)
        restart()
    }

    override fun closed(code: Int, reason: String) {
        println(code)
        println(reason)
        restart()
    }

    override fun failed(failure: Throwable, response: Response?) {
        when (failure) {
            is EOFException -> {
                println("Reached an EOF, restarting")
                restart()
            }
            is SocketException -> {
                println("Had a Socket error, restarting")
                restart()
            }
            is SocketTimeoutException -> {
                println("Socket timed out, restarting")
                restart()
            }
            else -> {
                failure.printStackTrace()
                TODO("not implemented")
            }
        }
    }
}
