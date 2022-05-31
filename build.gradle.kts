import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradleVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}")
        classpath("com.google.gms:google-services:${Versions.googleServicesVersion}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsVersion}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}