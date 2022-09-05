// TODO: Remove once https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(Plugins.androidLibrary)
    kotlin(KotlinPlugins.android)
    kotlin(KotlinPlugins.kapt)
    alias(libs.plugins.ksp)
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
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    kapt(libs.dagger.hilt.compiler)
}