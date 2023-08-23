package com.jessecorbett.diskord.testbot

import com.jessecorbett.diskord.api.common.ActionRow
import com.jessecorbett.diskord.api.common.Button
import com.jessecorbett.diskord.api.common.ButtonStyle
import com.jessecorbett.diskord.bot.bot
import com.jessecorbett.diskord.bot.classicCommands
import com.jessecorbett.diskord.bot.events
import com.jessecorbett.diskord.util.sendMessage
import com.jessecorbett.diskord.util.toTimestamp
import kotlinx.datetime.Clock

// Hack to get node envvars
external val process: Process

external interface Process {
    val env: dynamic
}

suspend fun main() {
    bot(process.env.DISKORD_JS_BOT.unsafeCast<String>()) {
        events {
            var started = false
            onReady {
                if (!started) {
                    channel("547517051556855808").sendMessage(
                        "Diskord JS bot has started, ${Clock.System.now().toTimestamp()}",
                        components = listOf(
                            ActionRow(
                                Button(
                                    url = "https://gitlab.com/diskord/diskord/-/blob/develop/diskord-testbot-js/src/jsMain/kotlin/com.jessecorbett.diskord.testbot/Bot.kt?ref_type=heads",
                                    label = "Bot Source",
                                    style = ButtonStyle.Link
                                )
                            )
                        )
                    )
                }
                setStatus("Making sure JS runtime works")
                started = true
            }
        }

        classicCommands {
            command("js") {
                it.respondAndDelete("JS bot is working!")
            }
        }
    }
}
