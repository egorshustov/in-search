object Build {

    private const val gradleBuildToolsPluginVersion = "7.1.3"
    const val gradleBuildTools = "com.android.tools.build:gradle:$gradleBuildToolsPluginVersion"

    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.kotlinVersion}"

    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.hiltVersion}"

    private const val googleServicesPluginVersion = "4.3.10"
    const val googleServicesPlugin = "com.google.gms:google-services:$googleServicesPluginVersion"

    private const val firebaseCrashlyticsPluginVersion = "2.9.0"
    const val firebaseCrashlyticsPlugin =
        "com.google.firebase:firebase-crashlytics-gradle:$firebaseCrashlyticsPluginVersion"
}