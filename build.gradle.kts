plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    id("maven-publish")
}

group = "dev.stashy.mongoservices"
version = "0.4.0"

publishing {
    repositories {
        maven("https://repo.stashy.dev/releases") {
            credentials {
                val repoUser: String? by project
                val repoToken: String? by project

                username = repoUser ?: ""
                password = repoToken ?: ""
            }
        }
    }
}