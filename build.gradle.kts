plugins {
    `maven-publish`
    signing

    id("org.jetbrains.kotlin.multiplatform") version "1.4.21" apply(false)
    id("org.jetbrains.dokka") version "1.4.10.2"
}

val diskordVersion: String by project

group = "com.jessecorbett"
version = diskordVersion

allprojects {
    repositories {
        mavenCentral()
        jcenter() // Needed for dokka
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(rootDir.resolve("public"))
}
