plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    macosX64("mac") {
        binaries {
            executable {
                entryPoint("com.jessecorbett.diskord.testbot.main")
            }
        }
    }

    sourceSets {
        val macMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-macosx64:1.6.4")
                implementation(project(":diskord-bot"))
            }
        }
    }
}
