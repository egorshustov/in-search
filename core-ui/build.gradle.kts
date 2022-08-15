plugins {
    id(Plugins.androidLibrary)
    kotlin(KotlinPlugins.android)
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
    implementation(project(":core-navigation"))

    api(libs.coil.kt.compose)
    // https://stackoverflow.com/questions/71812710/can-no-longer-view-jetpack-compose-previews-failed-to-instantiate-one-or-more-c
    // https://issuetracker.google.com/issues/227767363
    debugApi(AndroidX.customView) // TODO: remove after Google preview bug will be fixed
    debugApi(AndroidX.customViewPoolingContainer) // TODO: remove after Google preview bug will be fixed

    implementation(Compose.runtime)
    implementation(Compose.ui)
    implementation(Compose.material3)
    implementation(Compose.activity)
    debugImplementation(Compose.uiTooling)
    implementation(Compose.uiToolingPreview)
    implementation(Accompanist.systemUiController)
}