plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.aboutlibraries)
}

android {
    namespace = "com.cankolay.kapacitor.android.presentation"
    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.compileSdk
                .get()
                .toInt()
    }

    sourceSets.all {
        kotlin.srcDir(srcDir = "src/$name/kotlin")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.bundles.core)

    implementation(kotlin("reflect"))
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.compose.navigation)

    implementation(libs.bundles.lifecycle)

    implementation(libs.coil)

    implementation(libs.m3color)

    implementation(libs.aboutlibraries)
}