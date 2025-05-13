plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.dokka)
    id("maven-publish")
}

group = "dev.stashy.kongo"
version = "0.7.0-SNAPSHOT"

dependencies {
    dokka(projects.model)
    dokka(projects.services)
}
