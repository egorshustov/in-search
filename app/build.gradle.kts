import java.io.FileInputStream
import java.util.*

plugins {
    id(Plugins.androidApplication)
    kotlin(KotlinPlugins.android)
    kotlin(KotlinPlugins.kapt)
    id(Plugins.hilt)
    id(Plugins.googleServices)
    id(Plugins.firebaseCrashlytics)
}

kapt {
    correctErrorTypes = true
}

android {
    signingConfigs {
        create("release") {
            val localProps = Properties()
            val keyProps = Properties()
            try {
                localProps.load(FileInputStream(file("../local.properties")))
                keyProps.load(FileInputStream(file(localProps["keystore.props.file"]!!)))

                storeFile = file("$rootDir/egorshustov.jks")
                storePassword = keyProps["KEYSTORE_PASSWORD"].toString()
                keyAlias = keyProps["KEY_ALIAS"].toString()
                keyPassword = keyProps["KEY_PASSWORD"].toString()
            } catch (ignored: Exception) {
            }
        }
    }
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
            signingConfig = signingConfigs.getByName("release")
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
        kotlinCompilerExtensionVersion = Compose.composeVersion
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
    implementation(project(":core-ui"))
    implementation(project(":feature-auth"))
    implementation(project(":feature-params"))
    implementation(project(":feature-search"))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Google.material)
    implementation(Compose.material3)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.activity)
    implementation(AndroidX.lifecycleRuntimeKtx)

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

    implementation(AndroidX.workRuntimeKtx)
    implementation(Hilt.hiltWork)

    implementation(platform(Firebase.firebaseBom))
    implementation(Firebase.firebaseCrashlyticsKtx)
    implementation(Firebase.firebaseAnalyticsKtx)

    debugImplementation(Compose.uiTooling)
}