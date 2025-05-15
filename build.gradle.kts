import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.remove
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.google.protobuf")
    id("org.jlleitschuh.gradle.ktlint")
    kotlin("plugin.serialization")
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.dogacel:kotlinx-protobuf-gen:0.1.0")

    implementation(libs.bundles.kotlinx)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.get()}"
    }

    plugins {
        id("kotlinx-protobuf-gen") {
            artifact = "io.github.dogacel:kotlinx-protobuf-gen:0.1.0:jvm8@jar"
        }
    }

    // Enable Kotlin generation
    generateProtoTasks {
        all().forEach {
            it.builtins {
                remove("java")
            }
            it.plugins {
                id("kotlinx-protobuf-gen") {
                    option("package_prefix=custom.pkg")
                }
            }
        }
    }
}

tasks.withType(KotlinCompilationTask::class.java).configureEach {
    compilerOptions.freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
}

repositories {
    // Required to download KtLint
    mavenCentral()
}

// Optionally configure plugin
configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    ignoreFailures.set(true)
    filter {
        exclude { entry ->
            val condition =
                entry.file.toString().contains(".proto.kt") || entry.file.toString().contains("generated")
            condition
        }
    }
}

ktlint {
    filter {
        exclude { entry ->
            val condition =
                entry.file.toString().contains(".proto.kt") || entry.file.toString().contains("generated")
            condition
        }
    }
}
