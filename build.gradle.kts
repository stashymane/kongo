plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    id("maven-publish")
}

group = "dev.stashy.mongoservices"
version = "0.6.1"
