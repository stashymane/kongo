plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    id("maven-publish")
}

group = "dev.stashy"
version = "0.3.0"

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines)

    implementation(libs.mongodb.coroutine)
    implementation(libs.mongodb.bson.kotlinx)

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

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