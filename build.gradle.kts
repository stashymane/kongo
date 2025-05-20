plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.mavenPublish) apply false
    alias(libs.plugins.dokka)
}

group = "dev.stashy.kongo"
version = "0.8.0"

dependencies {
    dokka(projects.model.core)
    dokka(projects.model.bson)
    dokka(projects.services)
}

dokka {
    pluginsConfiguration.html {
        footerMessage = "Licensed under Apache 2.0"
        homepageLink = "https://github.com/stashymane/kongo"
    }
}
