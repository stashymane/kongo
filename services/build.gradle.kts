import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.mavenPublish)
}

group = rootProject.group
version = rootProject.version

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
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines)

            implementation(libs.mongodb.coroutine)
            implementation(libs.mongodb.bson.kotlinx)

            implementation(kotlin("reflect"))
        }

        commonTest.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-test")
            implementation(libs.testcontainers.core)
            implementation(libs.testcontainers.mongodb)
        }
    }
}

dokka {
    dokkaSourceSets.configureEach {
        sourceLink {
            localDirectory = rootDir
            remoteUrl("https://github.com/stashymane/kongo/tree/master")
            remoteLineSuffix = "#L"
        }
    }
}
