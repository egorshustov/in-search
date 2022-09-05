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
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))
    implementation(project(":core-database"))
    implementation(project(":core-datastore"))
    implementation(project(":core-network"))

    implementation(libs.androidx.room.paging)

    kapt(libs.dagger.hilt.compiler)
}