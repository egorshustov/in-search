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
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-navigation"))
    implementation(project(":core-model"))
    implementation(project(":core-domain"))

    implementation(Compose.material)
    implementation(Compose.uiToolingPreview)

    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltExtensionCompiler)

    implementation(AndroidX.workRuntimeKtx)
    implementation(Hilt.hiltWork)
}