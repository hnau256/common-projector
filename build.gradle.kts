import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    val kotlinVersion = "2.1.20"
    kotlin("multiplatform") version kotlinVersion
    id("com.android.library") version "8.7.2"
    id("org.jetbrains.compose") version "1.8.0"
    kotlin("plugin.compose") version kotlinVersion
    id("maven-publish")
    kotlin("plugin.serialization") version kotlinVersion
}

repositories {
    mavenCentral()
    google()
    mavenLocal()
    maven("https://jitpack.io")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

group = "com.github.hnau256"
version = "1.0.14"

android {
    namespace = "com.github.hnau256." + project.name.replace('-', '.')
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

kotlin {
    jvm()

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        publishLibraryVariants("release")
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)

                implementation("com.github.hnau256:common-kotlin:1.0.0")
                implementation("com.github.hnau256:common-dynamiccolor:1.0.0")

                val arrow = "1.2.4"
                implementation("io.arrow-kt:arrow-core:$arrow")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
            }
        }

        androidMain {
            dependencies {
                implementation("androidx.activity:activity-compose:1.10.1")
                implementation("androidx.appcompat:appcompat:1.7.0")
                implementation("com.github.hnau256.common-model:common-model-android:1.0.10")
            }
        }

        jvmMain {
            dependencies {
                implementation("com.github.hnau256.common-model:common-model-jvm:1.0.10")
            }
        }
    }
}

publishing {
    publications {
        configureEach {
            (this as MavenPublication).apply {
                groupId = project.group as String
                version = project.version as String
            }
        }
    }
}