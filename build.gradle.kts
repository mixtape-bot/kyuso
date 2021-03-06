import lol.dimensional.gradle.dsl.ReleaseType
import lol.dimensional.gradle.dsl.Version

buildscript {
    repositories {
        maven("https://maven.dimensional.fun/releases")
    }

    dependencies {
        classpath("fun.dimensional.gradle:gradle-tools:1.0.3")
    }
}

plugins {
    `maven-publish`
    kotlin("multiplatform") version "1.6.21"
}

val moduleVersion = Version(1, 0, 0, release = ReleaseType.Final)

project.group = "mixtape.oss"
project.version = moduleVersion.asString()

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()

    jvm()

    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    }

    sourceSets["jvmMain"].dependencies {
        implementation(kotlin("stdlib-jdk8"))
    }
}

publishing {
    repositories {
        maven {
            url = uri(moduleVersion.repository.fullUrl)

            authentication {
                create<BasicAuthentication>("basic")
            }

            credentials {
                username = System.getenv("REPO_ALIAS")?.toString()
                password = System.getenv("REPO_TOKEN")?.toString()
            }
        }
    }

    publications {
        create<MavenPublication>("Kyuso")
    }
}
