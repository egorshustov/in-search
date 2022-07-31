object Hilt {

    const val hiltVersion = "2.43.1"
    const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"

    private const val hiltExtensionCompilerVersion = "1.0.0"
    const val hiltExtensionCompiler = "androidx.hilt:hilt-compiler:$hiltExtensionCompilerVersion"

    private const val hiltNavigationComposeVersion = "1.0.0"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    private const val hiltWorkVersion = "1.0.0"
    const val hiltWork = "androidx.hilt:hilt-work:$hiltWorkVersion"
}