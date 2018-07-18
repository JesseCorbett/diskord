# Diskord [![](https://jitpack.io/v/com.jessecorbett/Diskord.svg)](https://jitpack.io/#com.jessecorbett/Diskord)
A Kotlin client for Discord bots

Built as a lean client using coroutines that gets the intricacies of rate limits, async, and data models out of your way.

Feel free to submit a PR or an Issue and I'll address it ASAP.

## How do I import this?

### Gradle
```
allprojects {
    repositories {
   	...
   	maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.jessecorbett:Diskord:0.0.1'
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
    <version>0.0.1</version>
</dependency>
```

## How do I use this?

Proper documentation needs to be written, but the concept for now is very simple.
Instantiate each client as you like to access the methods Discord offers for that particular resource.
To build a live bot, simply create a DiscordWebSocket with an EventListener, overriding the event methods you want to know about.

### Things to do
- Add more testing
- Add OAuth2 client
- Possibly a DSL for simple reactive bots
