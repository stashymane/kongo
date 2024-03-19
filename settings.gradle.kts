plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://repo.stashy.dev/releases")
    }
}


rootProject.name = "mongodb-services"
