pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
          // Add JitPack here
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

    }
}

rootProject.name = "SettingsPage"
include(":app")
 