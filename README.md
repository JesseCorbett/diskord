# Diskord - A Kotlin Discord SDK
[![Maven Central](https://img.shields.io/maven-central/v/com.jessecorbett/diskord-bot.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.jessecorbett%22%20AND%20a:%22diskord-bot%22)
[![Discord](https://img.shields.io/discord/424046347428167688.svg?style=flat-square)](https://discord.gg/UPTWsZ5)

A multiplatform Kotlin client for Discord bots with a simple and concise DSL supporting JVM and NodeJS

Built as a lean client using coroutines that gets the intricacies of rate limits, async, and data models out of your way in a clean and easy to use SDK

[Documentation available here](https://diskord.gitlab.io/diskord/)

Using Diskord? Drop by our [discord server](https://discord.gg/UPTWsZ5)

## How do I import this?

It is strongly recommended to use Gradle version 7 or higher

```kotlin
// Kotlin build.gradle.kts
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.jessecorbett:diskord-bot:5.4.0")
}
```

Note: The `diskord-bot` artifact bundles `org.slf4j:slf4j-simple` to provide basic logging to STDOUT with no
configuration. This can be excluded in favor of your own slf4j logger using gradle exclusion:

```kotlin
// Kotlin build.gradle.kts
configurations {
  implementation {
    exclude("org.slf4j", "slf4j-simple")
  }
}
```

The library is packaged into two artifacts.

`diskord-core` is the low level implementation of the Discord API.
[Read more](https://gitlab.com/diskord/diskord/-/blob/master/diskord-core/README.md)

`diskord-bot` provides an easier to use API for common bot functions.
[Read more](https://gitlab.com/diskord/diskord/-/blob/master/diskord-bot/README.md)

## How do I use this?

[Dokka documentation](https://diskord.gitlab.io/diskord/)

For an example project you can easily clone to get started, look at the [diskord-starter repo.](https://gitlab.com/diskord/diskord-starter)

There are also a collection of examples in the [diskord-examples repo.](https://gitlab.com/diskord/diskord-examples)

### Simple Example

```kotlin
import com.jessecorbett.diskord.bot.*
import com.jessecorbett.diskord.util.*

suspend fun main() {
    bot(TOKEN) {
        // Generic hook set for all events
        events {
            onGuildMemberAdd {
                channel(WELCOME_CHANNEL_ID).sendMessage("Welcome to the server, ${it.user?.username}!")
            }
        }
      
        // Modern interactions API for slash commands, user commands, etc
        interactions {
            slashCommand("echo", "Makes the bot say something") {
                val message by stringParameter("message", "The message", optional = true)
                callback {
                    respond {
                        content = if (message != null) {
                            message
                        } else {
                            "The message was null, because it is optional"
                        }
                    }
                }
            }

            commandGroup("emoji", "Send an emoji to the server", guildId = "424046347428167688") {
                subgroup("smile", "Smile emoji") {
                    slashCommand("slight", "A slight smile emoji") {
                        callback {
                            respond {
                                content = "🙂"
                            }
                        }
                    }
                }

                slashCommand("shh", "The shh emoji") {
                    val secret by stringParameter("secret", "Send the emoji secretly")
                    callback {
                        respond {
                            content = "🤫"
                            if (secret) {
                                ephemeral
                            }
                        }
                    }
                }
            }
        }
      
        // The old-fashioned way, it uses messages, such as .ping, for commands
        classicCommands("!") {
            command("ping") {
                it.respond("pong")
            }
        }
    }
}
```


## FAQ
* Does this support voice chat?
    * No, voice chat is not supported at this time. If you need it I recommend checking out another SDK
* Can I contact you to ask a question/contribute to the project/report a bug?
    * [We've got a discord server for just that](https://discord.gg/UPTWsZ5)
* What if I'm hip and cool, and I want to use a newer more ~~unstable~~ exciting version?
    * You can use our development versions by using the snapshot repository
    * Include https://oss.sonatype.org/content/repositories/snapshots/ in your gradle repositories
