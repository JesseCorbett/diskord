plugins {
    `maven-publish`
    signing

    id("org.jetbrains.kotlin.multiplatform") version "1.6.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10" apply false
    id("org.jetbrains.dokka") version "1.6.10"
}

val diskordVersion: String by project

group = "com.jessecorbett"
version = diskordVersion

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = "16.0.0"
}

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
