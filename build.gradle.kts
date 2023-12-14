import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    signing

    kotlin("multiplatform") version "1.9.21" apply false
    kotlin("plugin.serialization") version "1.9.21" apply false
    id("org.jetbrains.dokka") version "1.9.10"
}

val diskordVersion: String by project

group = "com.jessecorbett"
version = diskordVersion

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(rootDir.resolve("public"))
}
