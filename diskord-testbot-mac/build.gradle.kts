plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.versions)
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
                implementation(project(":diskord-bot"))

                implementation(libs.kotlinx.coroutines.coreMacosX64)
            }
        }
    }
}
