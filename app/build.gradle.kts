plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

kapt {
    correctErrorTypes = true
}

android {
    compileSdk = AppConfig.compileSdk
    defaultConfig {
        applicationId = AppConfig.appId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
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
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-navigation"))
    implementation(project(":feature-auth"))
    implementation(project(":feature-search"))

    implementation("androidx.core:core-ktx:${Versions.coreKtxVersion}")
    implementation("androidx.appcompat:appcompat:${Versions.appCompatVersion}")
    implementation("com.google.android.material:material:${Versions.materialVersion}")
    implementation("androidx.compose.material:material:${Versions.composeVersion}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}")
    implementation("androidx.activity:activity-compose:${Versions.activityComposeVersion}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtxVersion}")
    implementation("com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanistSystemUiControllerVersion}")

    implementation("com.google.dagger:hilt-android:${Versions.hiltVersion}")
    kapt("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")

    implementation("androidx.work:work-runtime-ktx:${Versions.workVersion}")
    implementation("androidx.hilt:hilt-work:${Versions.hiltWorkVersion}")

    implementation(platform("com.google.firebase:firebase-bom:${Versions.firebaseBomVersion}"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    debugImplementation("androidx.compose.ui:ui-tooling:${Versions.composeVersion}")
}