import java.net.URL

plugins {
    `maven-publish`
    signing

    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.versions)
}

group = rootProject.group
version = rootProject.version

val javadocJar by tasks.creating(Jar::class) {
    group = "build"
    dependsOn(tasks.dokkaHtml)
    archiveBaseName.set("${project.name}-jvm")
    archiveClassifier.set("javadoc")
    from("${layout.buildDirectory}/dokka/html")
}

tasks {
    dokkaHtmlPartial {
        dokkaSourceSets {
            configureEach {
                sourceLink {
                    // Unix based directory relative path to the root of the project (where you execute gradle respectively).
                    localDirectory.set(file("src/commonMain/kotlin"))
                    // URL showing where the source code can be accessed through the web browser
                    remoteUrl.set(URL(
                        "https://github.com/JesseCorbett/diskord/blob/master/diskord-core/src/commonMain/kotlin/"))
                    // Suffix which is used to append the line number to the URL. Use #L for GitHub
                    remoteLineSuffix.set("#L")
                }
                sourceLink {
                    localDirectory.set(file("src/jsMain/kotlin"))
                    remoteUrl.set(URL(
                        "https://github.com/JesseCorbett/diskord/blob/master/diskord-core/src/commonMain/kotlin/"))
                    remoteLineSuffix.set("#L")
                }
                sourceLink {
                    localDirectory.set(file("src/jvmMain/kotlin"))
                    remoteUrl.set(URL(
                        "https://github.com/JesseCorbett/diskord/blob/master/diskord-core/src/commonMain/kotlin/"))
                    remoteLineSuffix.set("#L")
                }
                sourceLink {
                    localDirectory.set(file("src/macMain/kotlin"))
                    remoteUrl.set(URL(
                        "https://github.com/JesseCorbett/diskord/blob/master/diskord-core/src/commonMain/kotlin/"))
                    remoteLineSuffix.set("#L")
                }
                sourceLink {
                    localDirectory.set(file("src/winMain/kotlin"))
                    remoteUrl.set(URL(
                        "https://github.com/JesseCorbett/diskord/blob/master/diskord-core/src/commonMain/kotlin/"))
                    remoteLineSuffix.set("#L")
                }
            }
        }
    }
}

kotlin {
    explicitApiWarning()

    jvm {
        mavenPublication {
            artifact(javadocJar)
        }
    }

    js(IR) {
        nodejs()

        mavenPublication {
            artifact(javadocJar)
        }
    }

    macosX64("mac") {

    }

    mingwX64("win") {

    }

    metadata {
        mavenPublication {
            artifact(javadocJar)
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
            languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
        }

        commonMain {
            dependencies {
                implementation(libs.kotlin.reflect)

                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.datetime)

                implementation(libs.logging.kotlinLogging)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.logging.core)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test.common)
                implementation(libs.kotlin.test.annotations.common)
            }
        }

        val jvmMain by getting {
            jvmToolchain {
                languageVersion.set(JavaLanguageVersion.of(8))
            }
            dependencies {
                implementation(libs.logging.slf4j.api)
                implementation(libs.logging.slf4j.simple)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.logging.jvm)
            }
        }
        val jvmTest by getting {
            jvmToolchain(8)
            dependencies {
                implementation(libs.kotlin.test.junit5)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.ktor.client.mock.jvm)
                implementation(libs.junit.jupiter.engine)
                implementation(libs.assertk.jvm)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }

        val macMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        val winMain by getting {
            dependencies {
                implementation(libs.ktor.client.winhttp)
            }
        }
    }
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            sourceLink {
                // Unix based directory relative path to the root of the project (where you execute gradle respectively).
                localDirectory.set(file("src"))

                remoteUrl.set(
                    URL(
                        "https://github.com/JesseCorbett/diskord/blob/master/diskord-bot/src"
                    )
                )
                remoteLineSuffix.set("#L")
            }
        }
    }
}


publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("diskord")
            description.set("A Kotlin wrapper around the Discord API")
            url.set("https://github.com/JesseCorbett/diskord")

            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("repo")
                }
            }

            developers {
                developer {
                    id.set("jesse corbett")
                    name.set("Jesse Corbett")
                    email.set("jesselcorbett@gmail.com")
                }
            }

            scm {
                url.set("https://github.com/JesseCorbett/diskord")
                connection.set("scm:git:https://github.com/JesseCorbett/diskord.git")
                developerConnection.set("scm:git:https://github.com/JesseCorbett/diskord.git")
            }
        }
    }

    repositories {
        maven {
            name = "gitlab"
            url = uri("https://gitlab.com/api/v4/projects/${System.getenv("CI_PROJECT_ID")}/packages/maven")

            credentials(HttpHeaderCredentials::class) {
                name = "Job-Token"
                value = System.getenv("CI_JOB_TOKEN")
            }

            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }

        maven {
            name = "ossrhSnapshots"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")

            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }

        maven {
            name = "ossrhStaging"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

val jvmTest by tasks.existing(Test::class) {
    useJUnitPlatform()

    systemProperty("com.jessecorbett.diskord.debug", project.findProperty("com.jessecorbett.diskord.debug") ?: false)
}

val signingKey: String? by project
val signingPassword: String? by project

signing {
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        publishing.publications.forEach { sign(it) }
    }
}
