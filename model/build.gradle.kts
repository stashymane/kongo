plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    id("maven-publish")
}

group = "dev.stashy.mongoservices"
version = rootProject.version

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.mongodb.bson.kotlinx)

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
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