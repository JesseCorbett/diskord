plugins {
    kotlin("jvm")
    application
}

application {
    mainClassName = "com.jessecorbett.diskord.testbot.BotKt"
}

dependencies {
    implementation(project(":diskord-bot"))
}
