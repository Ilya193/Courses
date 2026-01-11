pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Courses"
include(":app")

include(":core")
include(":core:navigation")
include(":core:ui")

include(":features")
include(":features:feature-root")

include(":features:auth")
include(":features:auth:api")
include(":features:auth:impl")

include(":features:main-menu")
include(":features:main-menu:api")
include(":features:main-menu:impl")

include(":features:courses")
include(":features:courses:api")
include(":features:courses:impl")

include(":core:courses-logic")
include(":core:courses-logic:courses-domain")
include(":core:courses-logic:courses-data")
include(":core:network")
include(":core:common")
