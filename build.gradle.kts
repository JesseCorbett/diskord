import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    signing

    id("org.jetbrains.kotlin.multiplatform") version "1.3.50"
    id("kotlinx-serialization") version "1.3.50"
    id("org.jetbrains.dokka") version "0.9.18"
}

val diskordVersion: String by project
val kotlinVersion: String by project
val kotlinxCoroutinesVersion: String by project
val ktorVersion: String by project
val okhttpVersion: String by project

group = "com.jessecorbett"
version = diskordVersion

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx") // kotlinx.serialization
}

val dokka by tasks.existing(DokkaTask::class) {
    outputFormat = "html"
    outputDirectory = "public"
}

val dokkaJavadoc by tasks.registering(DokkaTask::class) {
    outputFormat = "javadoc"
    outputDirectory = "$buildDir/javadoc"
}

val jvmJavadocJar by tasks.creating(Jar::class) {
    // FIXME: Dokka --> Javadoc generation is currently broken for annotations, so generate an empty JAR.
    //  See https://github.com/Kotlin/dokka/issues/464

    group = "build"
    // dependsOn(dokkaJavadoc)
    archiveBaseName.set("${project.name}-jvm")
    archiveClassifier.set("javadoc")
    // from("$buildDir/javadoc")
}

val metadataJavadocJar by tasks.creating(Jar::class) {
    group = "build"
    archiveBaseName.set("${project.name}-metadata")
    archiveClassifier.set("javadoc")
}

tasks.withType<DokkaTask> {
    noStdlibLink = false
    noJdkLink = false

    kotlinTasks {
        // dokka fails to retrieve sources from MPP-tasks so they must be set empty to avoid exception
        listOf()
    }
    sourceRoot {
        path = file("src/commonMain/kotlin").toString()
        platforms = listOf("Common")
    }
    sourceRoot {
        path = file("src/jvmMain/kotlin").toString()
        platforms = listOf("JVM")
    }
    // sourceRoot {
    //     path = file("src/jsMain/kotlin").toString()
    //     platforms = listOf("JS")
    // }

    linkMapping {
        dir = "src/commonMain/kotlin"
        url = "https://gitlab.com/jesselcorbett/diskord/tree/master/src/commonMain/kotlin"
        suffix = "#L"
    }
    linkMapping {
        dir = "src/jvmMain/kotlin"
        url = "https://gitlab.com/jesselcorbett/diskord/tree/master/src/jvmMain/kotlin"
        suffix = "#L"
    }
    // linkMapping {
    //     dir = "src/jsMain/kotlin"
    //     url = "https://gitlab.com/jesselcorbett/diskord/tree/master/src/jsMain/kotlin"
    //     suffix = "#L"
    // }
}

kotlin {
    jvm {
        mavenPublication {
            artifact(jvmJavadocJar)
        }
    }

    metadata {
        mavenPublication {
            artifact(metadataJavadocJar)
        }
    }

    // js {}

    sourceSets {
        commonMain {
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")

            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$kotlinxCoroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.12.0")
                implementation("io.github.microutils:kotlin-logging-common:1.6.26")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
            }
        }
        commonTest {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlinVersion")
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
                implementation("com.willowtreeapps.assertk:assertk:0.18")
                implementation("io.mockk:mockk-common:1.9.3")
            }
        }

        val jvmMain by getting {
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")

            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.12.0")
                implementation("io.github.microutils:kotlin-logging:1.6.26")
                implementation("org.slf4j:slf4j-api:1.7.26")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
                implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-junit5")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion")
                implementation("io.ktor:ktor-client-mock-jvm:$ktorVersion")
                implementation("org.junit.jupiter:junit-jupiter-engine:5.5.0")
                implementation("com.willowtreeapps.assertk:assertk-jvm:0.18")
                implementation("io.mockk:mockk:1.9.3")
                implementation("org.slf4j:slf4j-simple:1.7.26")
            }
        }

        // val jsMain by getting {
        //     dependencies {
        //         implementation("org.jetbrains.kotlin:kotlin-stdlib-js:$kotlinVersion")
        //         implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.10.0")
        //         implementation("io.github.microutils:kotlin-logging-js:1.6.25")
        //     }
        // }
        // val jsTest by getting {
        //     dependencies {
        //         implementation("org.jetbrains.kotlin:kotlin-test-js:$kotlinVersion")
        //     }
        // }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
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
    }

    publishing.publications.forEach { sign(it) }
}
