plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlinVersion
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
        kotlinCompilerExtensionVersion = Versions.composeVersion
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))

    kapt("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")

    implementation("io.ktor:ktor-client-core:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-client-content-negotiation:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-client-android:${Versions.ktorVersion}")
}