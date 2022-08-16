plugins {
    id(Plugins.androidLibrary)
    kotlin(KotlinPlugins.android)
    kotlin(KotlinPlugins.kapt)
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
        kotlinCompilerExtensionVersion = libs.versions.androidxCompose.get()
    }
}

dependencies {
    implementation(project(":core-navigation"))
    implementation(project(":core-common"))
    implementation(project(":core-domain"))
    implementation(project(":core-ui"))

    implementation(libs.androidx.compose.material3)

    kapt(libs.dagger.hilt.compiler)
}