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
    implementation(project(":core-common"))
    implementation(project(":core-navigation"))
    implementation(project(":core-model"))
    implementation(project(":core-domain"))

    implementation("androidx.compose.material:material:${Versions.composeVersion}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}")

    kapt("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")
    kapt("androidx.hilt:hilt-compiler:${Versions.hiltExtensionCompilerVersion}")

    implementation("androidx.work:work-runtime-ktx:${Versions.workVersion}")
    implementation("androidx.hilt:hilt-work:${Versions.hiltWorkVersion}")
}