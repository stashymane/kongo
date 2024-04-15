plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    id("maven-publish")
}

group = rootProject.group
version = rootProject.version

kotlin {
    jvm {
        jvmToolchain(11)
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.mongodb.bson.kotlinx)
        }

        commonTest.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-test")
        }
    }
}
