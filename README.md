# Diskord - A Kotlin Discord SDK
[![Maven Central](https://img.shields.io/maven-central/v/com.jessecorbett/diskord.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.jessecorbett%22%20AND%20a:%22diskord%22)
[![Discord](https://img.shields.io/discord/424046347428167688.svg?style=flat-square)](https://discord.gg/UPTWsZ5)

A Kotlin client for Discord bots with a simple and concise DSL

Built as a lean client using coroutines that gets the intricacies of rate limits, async, and data models out of your way in a clean and easy to use SDK

Using Diskord? Send me a tweet about it [@JesseLCorbett](https://twitter.com/JesseLCorbett) or drop by the [discord server](https://discord.gg/UPTWsZ5)

## How do I import this?

It is strongly recommended to use Gradle version 6 or higher

```kotlin
// Kotlin build.gradle.kts
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.jessecorbett:diskord-bot:2.0.0")
    // or, if you only want the low level implementation
    implementation("com.jessecorbett:diskord-core:2.0.0")
}
```

```groovy
// Groovy build.gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.jessecorbett:diskord-bot:2.0.0'
    // or, if you only want the low level implementation
    implementation 'com.jessecorbett:diskord-core:2.0.0'
}
```

Note: The `diskord-core` and `diskord-bot` artifacts bundle `org.slf4j:slf4j-simple` to provide basic logging to STDOUT with no
configuration. This can be excluded using the standard exclusion syntax:

```kotlin
// Kotlin build.gradle.kts
configurations {
  implementation {
    exclude("org.slf4j", "slf4j-simple")
  }
}
```

```groovy
// Groovy build.gradle
configurations {
  implementation {
    exclude 'org.slf4j', 'slf4j-simple'
  }
}
```

## How do I use this?

[Diskord Core Documentation](https://jesselcorbett.gitlab.io/diskord-core/diskord-core/index.html)

[Diskord Bot Documentation](https://jesselcorbett.gitlab.io/diskord-bot/diskord-bot/index.html)


## FAQ
* Does this support voice chat?
    * No, voice chat is not supported at this time. If you need it I recommend checking out another SDK
* Is this library done?
    * Diskord is actively maintained, but the API is always changing and there may be some lag between an API change and Diskord getting updated
    * If you want to speed things along, PRs are welcome and tickets appreciated
* Can I contact you to ask a question/contribute to the project/report a bug?
    * [We've got a discord server for just that!](https://discord.gg/UPTWsZ5)
* What if I'm hip and cool, and I want to use a newer more ~~unstable~~ exciting version?
    * You can use our development versions by using the snapshot repository
    * Include https://oss.sonatype.org/content/repositories/snapshots/ in your gradle repositories
