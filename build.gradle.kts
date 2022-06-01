// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.gradleBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltGradlePlugin)
        classpath(Build.googleServicesPlugin)
        classpath(Build.firebaseCrashlyticsPlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}