plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

kapt {
    correctErrorTypes = true
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
    implementation(project(":core-navigation"))
    implementation(project(":core-common"))
    implementation(project(":core-domain"))

    kapt("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")

    implementation("androidx.compose.material:material:${Versions.composeVersion}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}")
}