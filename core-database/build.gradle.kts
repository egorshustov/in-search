plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version Versions.kspVersion
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

    implementation("androidx.room:room-runtime:${Versions.roomVersion}")
    implementation("androidx.room:room-ktx:${Versions.roomVersion}")
    ksp("androidx.room:room-compiler:${Versions.roomVersion}")

    kapt("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")
}