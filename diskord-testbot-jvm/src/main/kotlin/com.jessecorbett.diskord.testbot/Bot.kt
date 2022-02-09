package com.jessecorbett.diskord.testbot

import com.jessecorbett.diskord.api.interaction.callback.InteractionCallbackType
import com.jessecorbett.diskord.api.interaction.callback.InteractionCommandCallbackData
import com.jessecorbett.diskord.api.interaction.callback.InteractionResponse
import com.jessecorbett.diskord.bot.bot
import com.jessecorbett.diskord.bot.classicCommands
import com.jessecorbett.diskord.bot.events
import com.jessecorbett.diskord.bot.interaction.interactions
import com.jessecorbett.diskord.util.sendMessage

suspend fun main() {
    bot(System.getenv("DISKORD_JVM_BOT")) {
        events {
            var started = false
            onReady {
                if (!started) {
                    channel("347593960417656834").sendMessage("Diskord JVM bot has started")
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

        interactions {
            slashCommand("echo", "Have the bot echo a command") {
                val message by stringParameter("echo", "Message you want the bot to say")
                val user by userParameter("name", "Which user to mock")

                callback {
                    it.client.createInteractionResponse(it.id, InteractionResponse(
                        type = InteractionCallbackType.ChannelMessageWithSource,
                        data = InteractionCommandCallbackData(content = "$user: $message")
                    ))
                }
            }
        }
    }
}
