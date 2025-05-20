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
            api(projects.model.core)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
