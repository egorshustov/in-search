plugins {
    id(Plugins.androidLibrary)
    kotlin(KotlinPlugins.android)
    kotlin(KotlinPlugins.kapt)
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
    api(Hilt.hiltAndroid)
    api(Hilt.hiltNavigationCompose)
    api("com.jakewharton.timber:timber:${Versions.timberVersion}")

    kapt(Hilt.hiltCompiler)
}
