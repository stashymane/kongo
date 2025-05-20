import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.jvm.toolchain.get())
    }

    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(libs.versions.jvm.target.get())
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.services)
            implementation(projects.sample.data)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines)

            implementation(libs.mongodb.coroutine)
            implementation(libs.mongodb.bson.kotlinx)
        }

        commonTest.dependencies {
            implementation(libs.testcontainers.core)
            implementation(libs.testcontainers.mongodb)
        }
    }
}
