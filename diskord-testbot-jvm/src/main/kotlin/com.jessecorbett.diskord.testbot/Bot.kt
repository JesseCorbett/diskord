package com.jessecorbett.diskord.testbot

import com.jessecorbett.diskord.api.common.NamedChannel
import com.jessecorbett.diskord.api.gateway.events.AvailableGuild
import com.jessecorbett.diskord.bot.bot
import com.jessecorbett.diskord.bot.classicCommands
import com.jessecorbett.diskord.bot.events
import com.jessecorbett.diskord.bot.interaction.interactions
import com.jessecorbett.diskord.util.sendMessage
import com.jessecorbett.diskord.util.toTimestamp
import kotlinx.datetime.Clock

suspend fun main() {
    bot(System.getenv("DISKORD_JVM_BOT")) {
        events {
            var started = false
            onReady {
                if (!started) {
                    val now = Clock.System.now()
                    channel("547517051556855808").sendMessage("Diskord JVM bot has started, ${now.toTimestamp()}")
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
            userCommand("print") {
                respond {
                    content = "Test data for interaction $data"
                    ephemeral
                }
            }

            messageCommand("test") {
                println(command)
                respond {
                    content = "OK"
                    ephemeral
                }
            }

            slashCommand("echo", "Makes the bot say something") {
                val message by stringParameter("message", "The message")
                callback {
                    respond {
                        content = message
                    }
                }
            }

            slashCommand("timestamp", "Prints the current timestamp") {
                callback {
                    respond {
                        content = Clock.System.now().toString()
                    }
                }
            }

            commandGroup("testing", "Test group", guildId = "341767204255039490") {
                subgroup("foo", "Foo test group") {
                    slashCommand("bar", "Bar command") {
                        val msg by stringParameter("message", "The message to say")
                        callback {
                            respond {
                                content = "$msg bar!"
                            }
                        }
                    }
                }

                slashCommand("buzz", "Buzz command") {
                    val msg by stringParameter("message", "The message to say")
                    callback {
                        respond {
                            content = "$msg buzz!"
                        }
                    }
                }
            }
        }
    }
}
