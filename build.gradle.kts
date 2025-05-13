plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    id("maven-publish")
}

group = "dev.stashy.kongo"
version = "0.7.0-SNAPSHOT"
