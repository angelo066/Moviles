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
include(":AndroidGame")
include(":desktop")
include(":Engine")
include(":GameLogic")
include(":DesktopEngine")
include(":DesktopGame")
include(":AndroidEngine")
