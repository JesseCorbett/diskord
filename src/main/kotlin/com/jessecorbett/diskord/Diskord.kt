package com.jessecorbett.diskord

import com.jessecorbett.diskord.exception.DiscordException

//val listener = DiscordWebSocket("MzQ2NDQ0NjE1ODMxNzgxMzc2.DXx1HQ.ZIsPln-2qpQa1l40MuLGwmyMkyw", WSListener())
val client = DiscordRestClient("MzQ2NDQ0NjE1ODMxNzgxMzc2.DXx1HQ.ZIsPln-2qpQa1l40MuLGwmyMkyw")

class WSListener: EventListener()

fun main(args: Array<String>) {
    while (true) {
        try {
//            client.createMessage("", CreateMessage("Test"))
        } catch (exception: DiscordException) {
            exception.printStackTrace()
            Thread.sleep(5000)
        }
    }
}
