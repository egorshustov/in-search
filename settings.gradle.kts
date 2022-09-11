dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "VPoiske"
include(
    ":app",

    ":core:common",
    ":core:data",
    ":core:database",
    ":core:datastore",
    ":core:domain",
    ":core:model",
    ":core:navigation",
    ":core:network",
    ":core:ui",

    ":feature:auth",
    ":feature:search",
    ":feature:params"
)
