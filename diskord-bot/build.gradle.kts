import java.net.URL

plugins {
    `maven-publish`
    signing

    alias(libs.plugins.kotlin.multiplatform)
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
    from("$buildDir/dokka/html")
}

tasks {
    dokkaHtmlPartial {
        dokkaSourceSets {
            configureEach {
                sourceLink {
                    localDirectory.set(file("src/commonMain/kotlin"))
                    remoteUrl.set(URL(
                        "https://gitlab.com/diskord/diskord/-/blob/develop/diskord-bot/src/commonMain/kotlin/"))
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
        }

        commonMain {
            dependencies {
                api(project(":diskord-core"))

                api(libs.logging.kotlinLogging)
            }
        }

        val jvmMain by getting {
            jvmToolchain {
                languageVersion.set(JavaLanguageVersion.of(8))
            }
            dependencies {
                implementation(libs.logging.slf4j.api)
                implementation(libs.logging.slf4j.simple)
            }
        }
    }
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("diskord-bot")
            description.set("A set of utilities for building bots using the diskord-core library")
            url.set("https://gitlab.com/jesselcorbett/diskord")

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
                url.set("https://gitlab.com/diskord/diskord")
                connection.set("scm:git:https://gitlab.com/diskord/diskord.git")
                developerConnection.set("scm:git:https://gitlab.com/diskord/diskord.git")
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

val signingKey: String? by rootProject
val signingPassword: String? by rootProject

signing {
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        publishing.publications.forEach { sign(it) }
    }
}
