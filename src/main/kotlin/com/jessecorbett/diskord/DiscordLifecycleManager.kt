package com.jessecorbett.diskord

import okhttp3.Response

interface DiscordLifecycleManager {
    fun start(restart: () -> Unit)

    fun closing(code: Int, reason: String)

    fun closed(code: Int, reason: String)

    fun failed(failure: Throwable, response: Response?)
}
