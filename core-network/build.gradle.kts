plugins {
    id(Plugins.androidLibrary)
    kotlin(KotlinPlugins.android)
    kotlin(KotlinPlugins.kapt)
    kotlin(KotlinPlugins.serialization) version Kotlin.kotlinVersion
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
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))

    kapt(Hilt.hiltCompiler)

    implementation(Ktor.core)
    implementation(Ktor.contentNegotiation)
    implementation(Ktor.jsonSerialization)
    implementation(Ktor.logging)
    implementation(Ktor.android)
}