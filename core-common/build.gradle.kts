plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
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
    api("androidx.compose.runtime:runtime:${Versions.composeVersion}")
    api("androidx.navigation:navigation-compose:${Versions.navigationComposeVersion}")
    api("com.google.dagger:hilt-android:${Versions.hiltVersion}")
    api("androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationComposeVersion}")
    api("com.jakewharton.timber:timber:${Versions.timberVersion}")

    kapt("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")
}
