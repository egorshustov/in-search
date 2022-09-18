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
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.runtime.livedata)
    api(libs.androidx.lifecycle.runtimeCompose)
    api(libs.coilKt.compose)

    debugApi(libs.androidx.compose.ui.tooling)
    // https://stackoverflow.com/questions/71812710/can-no-longer-view-jetpack-compose-previews-failed-to-instantiate-one-or-more-c
    // https://issuetracker.google.com/issues/227767363
    debugApi(libs.androidx.customview) // TODO: remove after Google preview bug will be fixed
    debugApi(libs.androidx.customview.poolingcontainer) // TODO: remove after Google preview bug will be fixed

    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.systemuicontroller)
}