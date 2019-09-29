import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

repositories {
    mavenCentral()
    maven(url = "https://kotlin.bintray.com/kotlinx") // kotlinx.serialization
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.jessecorbett:diskord:+")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
