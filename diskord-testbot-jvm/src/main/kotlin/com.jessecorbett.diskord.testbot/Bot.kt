package com.jessecorbett.diskord.testbot

import com.jessecorbett.diskord.api.common.NamedChannel
import com.jessecorbett.diskord.api.common.TextInput
import com.jessecorbett.diskord.api.gateway.events.AvailableGuild
import com.jessecorbett.diskord.bot.bot
import com.jessecorbett.diskord.bot.classicCommands
import com.jessecorbett.diskord.bot.events
import com.jessecorbett.diskord.bot.interaction.interactions
import com.jessecorbett.diskord.util.sendMessage
import com.jessecorbett.diskord.util.toTimestamp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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
                it.channel.sendMessage()
            }
        }

        interactions {
            userCommand("print") {
                respond {
                    content = "Test data for interaction $data"
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

                    command.createModal(
                        title = "Time Zone?",
                        TextInput("zone", label = "Timezone")
                    ) {
                        val tz = it.data.componentResponses.flatMap { it.components }.find { it.customId == "zone" }?.value

                        if (tz == null) {
                            respond {
                                content = "Timezone must be provided"
                                ephemeral
                            }
                        } else {
                            try {
                                val time = Clock.System.now().toLocalDateTime(TimeZone.of(tz))
                                respond {
                                    content = time.toString()
                                    ephemeral
                                }
                            } catch (e: Exception) {
                                respond {
                                    content = "$tz was not a valid time zone"
                                    ephemeral
                                }
                            }
                        }
                    }

                    respond {
                        content = Clock.System.now().toString()
                    }
                }
            }

            commandGroup("test", "Test group", guildId = "424046347428167688") {
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

                slashCommand("fizz", "Buzz command") {
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
