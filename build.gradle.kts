// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradlePlugin)
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.dagger.hilt.gradlePlugin)
        classpath(libs.gms.googleServices)
        classpath(libs.firebase.crashlyticsGradle)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}