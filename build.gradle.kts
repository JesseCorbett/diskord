plugins {
    `maven-publish`
    signing

    id("org.jetbrains.kotlin.multiplatform") version "1.4.32" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.32" apply false
    id("org.jetbrains.dokka") version "1.4.30"
}

val diskordVersion: String by project

group = "com.jessecorbett"
version = diskordVersion

allprojects {
    repositories {
        mavenCentral()
        jcenter {
            content {
                // TODO: Remove this once Dokka fully supports mavenCentral.
                //  This is the last remaining link to jcenter.
                includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
            }
        }
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
