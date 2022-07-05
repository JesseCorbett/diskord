import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    signing

    kotlin("multiplatform") version "1.6.21" apply false
    kotlin("plugin.serialization") version "1.6.21" apply false
    id("org.jetbrains.dokka") version "1.7.0"
}

val diskordVersion: String by project

group = "com.jessecorbett"
version = diskordVersion

rootProject.plugins.withType<NodeJsRootPlugin> {
    rootProject.the<NodeJsRootExtension>().nodeVersion = "16.15.1"
}

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
