plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    id("maven-publish")
}

group = rootProject.group
version = rootProject.version

kotlin {
    jvmToolchain(11)

    jvm()

    sourceSets {
        commonMain.dependencies {
            api(projects.model)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines)

            implementation(libs.mongodb.coroutine)
            implementation(libs.mongodb.bson.kotlinx)

            implementation(kotlin("reflect"))
        }

        commonTest.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-test")
        }
    }
}

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
