plugins {
    val kotlinVersion = "2.1.20"
    kotlin("plugin.serialization") version kotlinVersion
    id("com.android.library") version "8.2.2"
    kotlin("android") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.compose") version kotlinVersion
    id("maven-publish")
}

repositories {
    mavenCentral()
    google()
    maven("https://jitpack.io")
}

group = "com.github.hnau256"
version = "1.0.1"

android {
    namespace = "hnau.common.compose"

    defaultConfig {
        compileSdk = 35
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("com.github.hnau256:common-kotlin:1.0.0")
    implementation("com.github.hnau256:common-app:1.0.0")
    implementation("com.github.hnau256:common-color:1.0.2")
    val arrow = "1.2.4"
    implementation("io.arrow-kt:arrow-core:$arrow")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    val composeVersion = "1.8.0"
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material3:material3:1.3.2")
}

tasks {
    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        val javaDir = android.sourceSets["main"].java.srcDirs.first()
        val kotlinDir = File(javaDir.absolutePath.removeSuffix("java") + "kotlin")
        from(javaDir, kotlinDir)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            afterEvaluate {
                groupId = project.group as String
                artifactId = project.name
                version = project.version as String
                artifact(tasks.named("bundleReleaseAar"))
                artifact(tasks["sourcesJar"])
            }
        }
    }
}
