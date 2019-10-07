package com.jessecorbett.diskord.example

import com.jessecorbett.diskord.dsl.bot

suspend fun main() {
    bot("your-bot-token") {
        messageCreated {
            if (it.content == "ping") {
                it reply "pong"
            }
        }
    }
}
