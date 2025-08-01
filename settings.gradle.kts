pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        maven(url = "https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "Kapacitor"

include(":presentation")
include(":domain")
include(":data")
include(":app")