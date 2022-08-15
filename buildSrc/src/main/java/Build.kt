object Build {

    private const val gradleBuildToolsPluginVersion = "7.2.1"
    const val gradleBuildTools = "com.android.tools.build:gradle:$gradleBuildToolsPluginVersion"

    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.kotlinVersion}"

    private const val googleServicesPluginVersion = "4.3.13"
    const val googleServicesPlugin = "com.google.gms:google-services:$googleServicesPluginVersion"

    private const val firebaseCrashlyticsPluginVersion = "2.9.1"
    const val firebaseCrashlyticsPlugin =
        "com.google.firebase:firebase-crashlytics-gradle:$firebaseCrashlyticsPluginVersion"
}