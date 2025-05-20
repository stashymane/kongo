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
            api(projects.model.core)
            implementation(libs.kotlinx.serialization.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(kotlin("test"))
        }

        jvmMain.dependencies {
            api(libs.mongodb.bson.kotlinx)
        }
    }
}

mavenPublishing {
    coordinates(artifactId = "model-bson")

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
