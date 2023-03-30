package com.jessecorbett.diskord.testbot

import com.jessecorbett.diskord.api.common.NamedChannel
import com.jessecorbett.diskord.api.gateway.events.AvailableGuild
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

            onGuildCreate {
                if (it is AvailableGuild) {
                    println("==================")
                    println("Guild ID: " + it.id)
                    it.extras.channels.forEach { ch ->
                        if (ch is NamedChannel) {
                            println(ch.name)
                        } else {
                            println(ch.id)
                        }
                    }
                }
            }
        }

        classicCommands {
            command("jvm") {
                it.respondAndDelete("JVM bot is working!")
            }
        }

        interactions {
            userCommand("print") { it, data ->
                it.respond {
                    content = "Test data for interaction " + data.convertedUsersRolesChannels
                    ephemeral
                }
            }

            messageCommand("test") { interaction, data ->
                println(interaction)
            }

            slashCommand("echo", "Makes the bot say something") {
                val message by stringParameter("message", "The message")
                callback { interaction, _ ->
                    interaction.respond {
                        content = message
                    }
                }
            }
        }
    }
}
