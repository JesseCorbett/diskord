plugins {
    id("org.jetbrains.kotlin.js")
}

dependencies {
    implementation(project(":diskord-bot"))
}

kotlin {
    js(IR) {
        nodejs()
        binaries.executable()
    }
}
