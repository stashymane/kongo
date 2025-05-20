plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.mavenPublish)
}

group = rootProject.group
version = rootProject.version

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.model.core)
            api(projects.model.bson)
        }
    }
}

mavenPublishing {
    pom {
        name = "${project.group}:${project.name}"
    }
}
