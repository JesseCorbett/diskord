plugins {
    `maven-publish`
    signing

    id("org.jetbrains.kotlin.multiplatform") version "1.6.20" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.20" apply false
    id("org.jetbrains.dokka") version "1.6.10"
}

val diskordVersion: String by project

group = "com.jessecorbett"
version = diskordVersion

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(rootDir.resolve("public"))
}
