plugins {
    kotlin("jvm") version "2.0.0" apply false
    kotlin("plugin.serialization") version "2.0.0" apply false

    id("com.gradle.develocity") version ("3.19.2")
    id("com.google.protobuf") version "0.9.4" apply false
    id("org.jetbrains.dokka") version "2.0.0" apply false
    id("org.jetbrains.kotlinx.kover") version "0.9.1" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1" apply false
    id("de.fayard.refreshVersions") version "0.60.5"
}

rootProject.name = "protobuf-gen-sample"