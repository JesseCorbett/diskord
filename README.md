# Diskord
[![Maven Central](https://img.shields.io/maven-central/v/com.jessecorbett/diskord.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.jessecorbett%22%20AND%20a:%22diskord%22)
[![Discord](https://img.shields.io/discord/424046347428167688.svg?style=flat-square)](https://discord.gg/UPTWsZ5)

A Kotlin client for Discord bots with a simple and concise DSL

Built as a lean client using coroutines that gets the intricacies of rate limits, async, and data models out of your way in a clean and easy to use SDK.

Feel free to submit a PR or an Issue and I'll address it ASAP.

Using Diskord? Send me a tweet about it! [@JesseLCorbett](https://twitter.com/JesseLCorbett) or drop by our [Discord server.](https://discord.gg/UPTWsZ5)

## How do I import this?

### Gradle
```groovy
repositories {
    mavenCentral()
    jcenter() // Necessary for kotlinx.serialization, until it is published in maven central too
}

dependencies {
    // Only if gradle >= 5.3
    implementation 'com.jessecorbett:diskord:1.5.2'

    // Valid for all gradle versions
    implementation 'com.jessecorbett:diskord-jvm:1.5.2'
}
```

### Maven
```xml
<dependency>
    <groupId>com.jessecorbett</groupId>
    <artifactId>diskord-jvm</artifactId>
    <version>1.5.2</version>
</dependency>
```

## How do I use this?

Simply instantiate a bot using the bot DSL, such as in the examples below.

Any function in the scope of the DSL will have access to a ClientStore `clientStore` to access clients for the bot user.

Additionally, extensions on the bot DSL, like the command DSL, can be done simply by writing extension functions which hook into the bot DSL on instantiation.

You can find more examples [here.](https://gitlab.com/jesselcorbett/diskord/tree/master/examples)

You can access the documentation [here.](https://jesselcorbett.gitlab.io/diskord/diskord/)

### Ping Pong Example
```kotlin
const val BOT_TOKEN = "A-Totally-Real-Discord-Bot-Token"

suspend fun main() {
    bot(BOT_TOKEN) {
        commands {
            command("ping") {
                reply("pong")
                delete()
            }
        }
    }
}
```

### Echo Example
```kotlin
const val BOT_TOKEN = "A-Totally-Real-Discord-Bot-Token"

suspend fun main() {
    bot(BOT_TOKEN) {
        commands {
            command("echo") {
                reply(words.drop(1).joinToString(" "))
                delete()
            }
        }
    }
}
```

### Embed Example
```kotlin
const val BOT_TOKEN = "A-Totally-Real-Discord-Bot-Token"

suspend fun main() {
    bot(BOT_TOKEN) {
        // Defaults to using command prefix `.` if unspecified
        commands("!") {
            command("embed") {
                delete()
                reply {
                    text = "This is an embed"
                    title = "Embed title"
                    description = "You can declare all the things here"
                }
            }
        }
    }
}
```

### Reaction Example
```kotlin
const val BOT_TOKEN = "A-Totally-Real-Discord-Bot-Token"

suspend fun main() {
    bot(BOT_TOKEN) {
        messageCreated {
            if (it.content.contains("diskord")) {
                it.react("💯")
            }
        }
    }
}
```

### Combined Example
```kotlin
const val BOT_TOKEN = "A-Totally-Real-Discord-Bot-Token"

suspend fun main() {
    bot(BOT_TOKEN) {
        messageCreated {
            if (it.content.contains("diskord")) {
                it.react("💯")
            }
        }
        
        // Defaults to using command prefix `.`
        commands {
            command("ping") {
                reply("pong")
                delete()
            }
                
            command("echo") {
                reply(words.drop(1).joinToString(" "))
                delete()
            }            
        }
    }
}
```

## FAQ
* Does this support voice chat?
    * No, voice chat is not supported at this time. If you need it I recommend checking out [JDA](https://github.com/DV8FromTheWorld/JDA) or [Discord4J](https://github.com/Discord4J/Discord4J)
* Is this library done?
    * It still needs some tests written, but Diskord is actively maintained and API complete, so it should be safe to use
* Can I contact you to ask a question/contribute to the project/report a bug/tell you this is all shit?
    * [Go for it!](https://discord.gg/UPTWsZ5)
* What if I'm hip and cool, and I want to use a newer more ~~unstable~~ exciting version?
    * You can use our development versions by using the snapshot repository
        * Repository URL https://oss.sonatype.org/content/repositories/snapshots/
        * Artifact directory https://gitlab.com/jesselcorbett/diskord/-/packages

## Things to do
- Add more testing
- Voice support
