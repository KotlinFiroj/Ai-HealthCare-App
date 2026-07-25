pluginManagement {
    includeBuild("build-logic")
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

rootProject.name = "Ai-HealthCare-App"

// App Module
include(":app")

// Core Modules
include(":core:common")
include(":core:designsystem")
include(":core:domain")
include(":core:data")
include(":core:network")
include(":core:database")
include(":core:ui")
include(":core:analytics")
include(":core:security")
include(":core:ai")
include(":core:testing")
include(":core:navigation")
include(":core:util")

// Feature Modules
include(":feature:auth")
include(":feature:home")
include(":feature:appointment")
include(":feature:reports")
include(":feature:reminder")
include(":feature:emergency")
include(":feature:healthtimeline")
include(":feature:chatbot")
include(":feature:ai")
