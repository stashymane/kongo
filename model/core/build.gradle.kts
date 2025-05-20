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

    mingwX64()

    linuxX64()
    linuxArm64()

    macosX64()
    macosArm64()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    js {
        browser {
            testTask {
                enabled = false
            }
        }
        nodejs()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                enabled = false
            }
        }
        nodejs()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmWasi {
        nodejs()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(kotlin("test"))
        }

        jvmMain.dependencies {
            compileOnly(libs.mongodb.bson.kotlinx)
        }

        val nonJvmMain by creating {
            dependsOn(commonMain.get())
        }

        listOf(nativeMain, jsMain, wasmJsMain, wasmWasiMain).forEach {
            it.get().dependsOn(nonJvmMain)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

mavenPublishing {
    coordinates(artifactId = "model-core")

    pom {
        name = "${project.group}:${project.name}"
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
