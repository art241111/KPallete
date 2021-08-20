plugins {
    id("org.jetbrains.compose") version "1.0.0-alpha3"
    id("com.android.application")
    kotlin("android")
}

group = "com.ger"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.3.0")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.ger.android"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}