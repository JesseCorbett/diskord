plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.versions)
}

kotlin {
    js(IR) {
        nodejs()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":diskord-bot"))
            }
        }
    }
}
