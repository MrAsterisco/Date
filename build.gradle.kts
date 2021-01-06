import org.jetbrains.kotlin.konan.target.HostManager
import java.net.URI
import java.util.*

plugins {
    kotlin("multiplatform") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.10"
    id("maven-publish")
}

group = project.property("group")!!
version = project.property("version")!!

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/mrasterisco/Maven")
}

kotlin {

    targets {
        jvm {
            compilations.all {
                kotlinOptions.jvmTarget = "1.8"
            }
            testRuns["test"].executionTask.configure {
                useJUnit()
            }
        }

        ios()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.1")
                implementation("io.github.mrasterisco:Time:1.6.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.1")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.1")
            }
        }
        val iosTest by getting
    }

    plugins.withId("maven-publish") {
        // https://github.com/gradle/gradle/issues/11412#issuecomment-555413327
        System.setProperty("org.gradle.internal.publish.checksums.insecure", "true")

        configure<PublishingExtension> {
            val vcs: String by project
            val bintrayOrg: String by project
            val bintrayRepository: String by project
            val bintrayPackage: String by project

            repositories {
                val local = Properties()
                val localProperties: File = rootProject.file("local.properties")
                if (localProperties.exists()) {
                    localProperties.inputStream().use { local.load(it) }
                }

                maven {
                    name = "bintray"
                    url =
                        URI("https://api.bintray.com/maven/$bintrayOrg/$bintrayRepository/$bintrayPackage/;publish=0;override=0")
                    credentials {
                        username = local["bintrayUser"] as String?
                        password = local["bintrayApiKey"] as String?
                    }
                }
            }

            publications.withType<MavenPublication> {
                pom {
                    name.set(project.name)
                    description.set(project.description)
                    url.set(vcs)
                    licenses {
                        license {
                            name.set("MIT")
                            url.set("$vcs/blob/master/LICENCE.md")
                            distribution.set("repo")
                        }
                    }
                    developers {
                        developer {
                            id.set(bintrayOrg)
                            name.set("Alessio Moiso")
                        }
                    }
                    scm {
                        connection.set("$vcs.git")
                        developerConnection.set("$vcs.git")
                        url.set(vcs)
                    }
                }
            }

            val taskPrefixes = when {
                HostManager.hostIsLinux -> listOf(
                    "publishLinux",
                    "publishJs",
                    "publishJvm",
                    "publishMetadata",
                    "publishKotlinMultiplatform"
                )
                HostManager.hostIsMac -> listOf("publishMacos", "publishIos")
                HostManager.hostIsMingw -> listOf("publishMingw")
                else -> error("Unknown host, abort publishing.")
            }

            val publishTasks = tasks.withType<PublishToMavenRepository>().matching { task ->
                taskPrefixes.any { task.name.startsWith(it) }
            }

            tasks.register("publishAll") { dependsOn(publishTasks) }
        }
    }
}
