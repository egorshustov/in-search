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
        kotlinCompilerExtensionVersion = libs.versions.androidxCompose.get()
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-navigation"))
    implementation(project(":core-model"))
    implementation(project(":core-domain"))
    implementation(project(":core-ui"))

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.hilt.work)

    kapt(libs.dagger.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
}