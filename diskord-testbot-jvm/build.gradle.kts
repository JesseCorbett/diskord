plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.versions)
    application
}

application {
    mainClass.set("com.jessecorbett.diskord.testbot.BotKt")
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":diskord-bot"))
            }
        }
    }
}
