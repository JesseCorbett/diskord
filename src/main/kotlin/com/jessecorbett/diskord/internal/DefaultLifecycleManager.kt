package com.jessecorbett.diskord.internal

import com.jessecorbett.diskord.DiscordLifecycleManager
import okhttp3.Response
import java.io.EOFException

class DefaultLifecycleManager : DiscordLifecycleManager {
    private lateinit var restart: () -> Unit

    override fun start(restart: () -> Unit) {
        this.restart = restart
    }

    override fun closing(code: Int, reason: String) {
        println(code)
        println(reason)
        TODO("not implemented")
    }

    override fun closed(code: Int, reason: String) {
        println(code)
        println(reason)
        TODO("not implemented")
    }

    override fun failed(failure: Throwable, response: Response?) {
        when (failure) {
            is EOFException -> {
                println("Reached an EOF, restarting")
                restart()
            }
            else -> {
                failure.printStackTrace()
                TODO("not implemented")
            }
        }
    }
}
