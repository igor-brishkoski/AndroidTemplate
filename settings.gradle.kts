enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libraries") {
            from(files("gradle/libraries.toml"))
        }
    }

    versionCatalogs {
        create("testLibraries") {
            from(files("gradle/testLibraries.toml"))
        }
    }

    versionCatalogs {
        create("androidTestLibraries") {
            from(files("gradle/androidTestLibraries.toml"))
        }
    }
}

rootProject.name = "AndroidTemplate"
include(":app")
