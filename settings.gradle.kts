pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Practica1"
include(":movil")
include(":desktop")
include(":interfaces")
include(":mastermind")
include(":desktop_engine")
include(":android_engine")
