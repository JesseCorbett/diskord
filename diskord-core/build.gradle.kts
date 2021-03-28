import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
    `maven-publish`
    signing

    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.dokka")
}

val kotlinVersion: String by project
val kotlinxCoroutinesVersion: String by project
val kotlinSerializationVersion: String by project
val ktorVersion: String by project
val okhttpVersion: String by project
val kotlinLoggingVersion: String by project
val slf4jVersion: String by project
val assertkVersion: String by project
val mockkVersion: String by project

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
                    // Unix based directory relative path to the root of the project (where you execute gradle respectively).
                    localDirectory.set(file("src/commonMain/kotlin"))
                    // URL showing where the source code can be accessed through the web browser
                    remoteUrl.set(URL(
                        "https://gitlab.com/jesselcorbett/diskord/-/blob/develop/diskord-core/src/commonMain/kotlin/"))
                    // Suffix which is used to append the line number to the URL. Use #L for GitHub
                    remoteLineSuffix.set("#L")
                }
                sourceLink {
                    localDirectory.set(file("src/jsMain/kotlin"))
                    remoteUrl.set(URL(
                        "https://gitlab.com/jesselcorbett/diskord/-/blob/develop/diskord-bot/src/jsMain/kotlin/"))
                    remoteLineSuffix.set("#L")
                }
                sourceLink {
                    localDirectory.set(file("src/jvmMain/kotlin"))
                    remoteUrl.set(URL(
                        "https://gitlab.com/jesselcorbett/diskord/-/blob/develop/diskord-bot/src/jvmMain/kotlin/"))
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
        browser()
        binaries.executable()

        mavenPublication {
            artifact(javadocJar)
        }
    }

    metadata {
        mavenPublication {
            artifact(javadocJar)
        }
    }

    sourceSets {
        commonMain {
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")
            languageSettings.useExperimentalAnnotation("kotlin.js.ExperimentalJsExport")

            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
                implementation("org.jetbrains.kotlin:kotlin-reflect")
                implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
            }
        }
        commonTest {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlinVersion")
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
                implementation("com.willowtreeapps.assertk:assertk:$assertkVersion")
                implementation("io.mockk:mockk-common:$mockkVersion")
            }
        }

        val jvmMain by getting {
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")
            languageSettings.useExperimentalAnnotation("kotlin.js.ExperimentalJsExport")

            dependencies {
                implementation("org.slf4j:slf4j-api:$slf4jVersion")
                implementation("org.slf4j:slf4j-simple:$slf4jVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-junit5")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion")
                implementation("io.ktor:ktor-client-mock-jvm:$ktorVersion")
                implementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
                implementation("com.willowtreeapps.assertk:assertk-jvm:$assertkVersion")
                implementation("io.mockk:mockk:$mockkVersion")
            }
        }

        val jsMain by getting {
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")
            languageSettings.useExperimentalAnnotation("kotlin.js.ExperimentalJsExport")

            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-js:$kotlinVersion")
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
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
                        "https://gitlab.com/jesselcorbett/diskord/-/tree/master/diskord-core/src"
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
            url.set("https://gitlab.com/jesselcorbett/diskord")

            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
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
                url.set("https://gitlab.com/jesselcorbett/diskord")
                connection.set("scm:git:https://gitlab.com/jesselcorbett/diskord.git")
                developerConnection.set("scm:git:https://gitlab.com/jesselcorbett/diskord.git")
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
