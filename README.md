# Diskord [![](https://jitpack.io/v/com.jessecorbett/Diskord.svg)](https://jitpack.io/#com.jessecorbett/Diskord) [![Discord](https://img.shields.io/discord/424046347428167688.svg?style=flat-square)](https://discord.gg/UPTWsZ5)

A Kotlin client for Discord bots

Built as a lean client using coroutines that gets the intricacies of rate limits, async, and data models out of your way.

Feel free to submit a PR or an Issue and I'll address it ASAP.

Using Diskord? Send me a tweet about it! [@JesseLCorbett](https://twitter.com/JesseLCorbett) or drop by the [Discord server](https://discord.gg/UPTWsZ5)

## How do I import this?

### Gradle
```
allprojects {
    repositories {
   	maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.jessecorbett:Diskord:0.0.14'
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
    <version>0.0.14</version>
</dependency>
```

## How do I use this?

Proper documentation needs to be written, but the concept for now is very simple.
Instantiate each client as you like to access the methods Discord offers for that particular resource.
To build a live bot, simply create a DiscordWebSocket with an EventListener, overriding the event methods you want to know about.

### Ping Pong Example
```kotlin
const val botToken = "A-Totally-Real-Discord-Bot-Token"

class BotListener : EventListener() {
    override suspend fun onMessageCreate(message: Message) {
        if (message.content == "ping") {
            val channelClient = ChannelClient(token, message.channelId)
            channelClient.sendMessage("pong")
        }
    }
}

fun main(args: Array<String>) {
    DiscordWebSocket(botToken, BotListener())
}
```

### Things to do
- Convenience methods
- Add more testing
- Proper documentation
- Add OAuth2 client
- Possibly a DSL for simple reactive bots
