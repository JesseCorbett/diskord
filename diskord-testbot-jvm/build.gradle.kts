plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.versions)
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
