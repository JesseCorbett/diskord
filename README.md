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


### Things to do
- Add more testing
- Add OAuth2 client
- Possibly a DSL for simple reactive bots
