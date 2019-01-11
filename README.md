# Diskord [![](https://jitpack.io/v/com.jessecorbett/Diskord.svg)](https://jitpack.io/#com.jessecorbett/Diskord) [![Discord](https://img.shields.io/discord/424046347428167688.svg?style=flat-square)](https://discord.gg/UPTWsZ5)

A Kotlin client for Discord bots with a simple and concise DSL

Built as a lean, opinionated client using coroutines that gets the intricacies of rate limits, async, and data models out of your way in a clean and easy to use DSL.

Feel free to submit a PR or an Issue and I'll address it ASAP.

Using Diskord? Send me a tweet about it! [@JesseLCorbett](https://twitter.com/JesseLCorbett) or drop by our [Discord server.](https://discord.gg/UPTWsZ5)

## How do I import this?

### Gradle
```
repositories {
   	maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.jessecorbett:Diskord:1.0.0'
}
```

### Maven
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.jessecorbett</groupId>
    <artifactId>Diskord</artifactId>
    <version>1.0.0</version>
</dependency>
```

## How do I use this?

Simply instantiate a bot using the bot DSL, such as in the examples below.

Nearly all discord API and bot gateway features can be accessed from the DSL!

You can access the documentation [here.](https://jessecorbett.github.io/Diskord/diskord/)

### Ping Pong Example
```kotlin
const val BOT_TOKEN = "A-Totally-Real-Discord-Bot-Token"

fun main() {
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

fun main() {
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

### Reaction Example
```kotlin
const val BOT_TOKEN = "A-Totally-Real-Discord-Bot-Token"

fun main() {
    bot(BOT_TOKEN) {
        messageCreated {
            if (it.content.contains("diskord")) {
                it.react("💯")
            }
        }
    }
}
```

## FAQ
* Does this support voice chat?
    * No, voice chat is not supported at this time. If you need it I recommend checking out [JDA](https://github.com/DV8FromTheWorld/JDA) or [Discord4J](https://github.com/Discord4J/Discord4J)
* Is this library production ready?
    * It still needs some tests written, but Diskord is actively maintained, so it should be safe to use for a real program
* Can I contact you to ask a question/contribute to the project/report a bug/tell you this is all shit?
    * Go for it!

## Things to do
- Add more testing
- Voice support
- Multiplatform support
