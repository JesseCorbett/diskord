package com.jessecorbett.diskord.testbot

import com.jessecorbett.diskord.bot.bot
import com.jessecorbett.diskord.bot.classicCommands
import com.jessecorbett.diskord.bot.events
import com.jessecorbett.diskord.util.sendMessage

suspend fun main() {
    bot(System.getenv("DISKORD_JVM_BOT")) {
        events {
            var started = false
            onReady {
                if (!started) {
                    channel("547517051556855808").sendMessage("Diskord JVM bot has started")
                }
                setStatus("Making sure JVM runtime works")
                started = true
            }
        }

        classicCommands {
            command("jvm") {
                it.respondAndDelete("JVM bot is working!")
            }
        }
    }
}
