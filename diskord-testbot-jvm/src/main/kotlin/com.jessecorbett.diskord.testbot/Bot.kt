package com.jessecorbett.diskord.testbot

import com.jessecorbett.diskord.api.interaction.callback.ChannelMessageWithSource
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

        interactions {
            userCommand("echo") { it, data ->
                it.client.createInteractionResponse(it.id, ChannelMessageWithSource(
                    data = ChannelMessageWithSource.Data(content = "Test data for interaction " + data.convertedUsersRolesChannels)
                )
                )
            }

            messageCommand("test") { interaction, data ->
                println(interaction)
            }

            slashCommand("echo", "Makes the bot say something") {
                val message by stringParameter("message", "The message")
                callback { applicationCommand, chatData ->
                    applicationCommand.client.createInteractionResponse(applicationCommand.id, ChannelMessageWithSource(
                        data = ChannelMessageWithSource.Data(content = message)
                    ))
                }
            }
        }
    }
}
