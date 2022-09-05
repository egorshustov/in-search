// TODO: Remove once https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(Plugins.androidLibrary)
    kotlin(KotlinPlugins.android)
    kotlin(KotlinPlugins.kapt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    compileSdk = AppConfig.compileSdk
    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    kapt(libs.dagger.hilt.compiler)
}