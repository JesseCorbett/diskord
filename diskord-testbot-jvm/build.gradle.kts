plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.jessecorbett.diskord.testbot.BotKt")
}

dependencies {
    implementation(project(":diskord-bot"))
}
