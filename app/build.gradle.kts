@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.aboutlibraries)
}

android {
    namespace = "com.cankolay.kapacitor.android"
    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = "com.cankolay.kapacitor.android"

        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.compileSdk
                .get()
                .toInt()

        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        named("debug") {
            storeFile = rootProject.file("app-key.keystore")
            storePassword = "android"
            keyAlias = "android"
            keyPassword = "android"
        }
    }

    sourceSets.all {
        kotlin.srcDir(srcDir = "src/$name/kotlin")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            signingConfig = signingConfigs.getByName("debug")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }

        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true

            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    androidResources { generateLocaleConfig = true }

    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

dependencies {
    implementation(kotlin("reflect"))

    implementation(libs.bundles.core)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.compose.navigation)

    implementation(libs.bundles.lifecycle)

    implementation(libs.datastore)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.bundles.ktor)

    implementation(libs.m3color)

    implementation(libs.aboutlibraries)

    implementation(libs.coil)
}